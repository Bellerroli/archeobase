package hu.belrol.archeobase.meret;

import hu.belrol.archeobase.db.DatabaseManager;
import hu.belrol.archeobase.strat.StratMapper;

import java.sql.*;
import java.util.List;

public class MeretDao {
    private static MeretDao instance;

    private MeretDao() {
    }

    public static MeretDao getInstance() {
        if (instance == null) {
            instance = new MeretDao();
        }
        return instance;
    }

    public Meret insert(Meret meret) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(MeretQuery.INSERT.getQuery(), Statement.RETURN_GENERATED_KEYS);
        int idx = 1;
        ps.setDouble(idx++, meret.getMeret());
        ps.setString(idx++, meret.getMeretTipus());
        ps.setString(idx++, meret.getMertekegyseg().toString());
        ps.setInt(idx, meret.getStratId());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        meret.setId(rs.getInt(1));
        connection.close();
        return meret;
    }

    public List<Meret> findByStratId(final int stratId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(MeretQuery.FIND_BY_STRATID.getQuery());
        ps.setInt(1, stratId);
        ResultSet rs = ps.executeQuery();
        List<Meret> merets = MeretMapper.mapList(rs);
        connection.close();
        return merets;
    }

    public List<String> findAttributeList(String attribute) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = MeretQuery.FIND_ALL_ATTRIBUTE.getQuery()
                .replace("{col}", attribute);
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<String> strings = StratMapper.getInstance().mapAttribute(attribute, rs);
        connection.close();
        return strings;
    }
}
