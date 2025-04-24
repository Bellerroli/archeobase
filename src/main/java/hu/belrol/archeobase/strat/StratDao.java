package hu.belrol.archeobase.strat;

import hu.belrol.archeobase.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StratDao {
    private static StratDao instance;

    private StratDao() {
    }

    public static StratDao getInstance() {
        if (instance == null) {
            instance = new StratDao();
        }
        return instance;
    }

    public Strat insertPartialStrat(Strat strat) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(StratQuery.INSERT_PARTIAL.getQuery(), Statement.RETURN_GENERATED_KEYS);
        int idx = 1;
        ps.setInt(idx++, strat.getSNR());
        ps.setInt(idx++, strat.getOBNR());
        ps.setString(idx++, strat.getKor());
        ps.setString(idx++, strat.getTipus());
        ps.setInt(idx, strat.getAsatasId());
        ps.executeUpdate();
        strat.setId(ps.getGeneratedKeys().getInt(1));
        connection.close();
        return strat;
    }

    public Strat insertStrat(Strat strat) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(StratQuery.INSERT_FULL.getQuery(), Statement.RETURN_GENERATED_KEYS);
        int idx = 1;
        ps.setInt(idx++, strat.getSNR());
        ps.setInt(idx++, strat.getOBNR());
        ps.setString(idx++, strat.getTipus());
        ps.setString(idx++, strat.getAllapot());
        ps.setString(idx++, strat.getLeiras());
        ps.setInt(idx++, strat.isFeltDat() ? 1 : 0);
        ps.setInt(idx++, strat.getAsatasId());
        ps.setString(idx++, strat.getKor());
        ps.setString(idx, strat.getKorszak());
        ps.executeUpdate();
        strat.setId(ps.getGeneratedKeys().getInt(1));
        connection.close();
        return strat;
    }

    public Strat update(Strat strat) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(StratQuery.UPDATE.getQuery(), Statement.RETURN_GENERATED_KEYS);
        int idx = 1;
        ps.setInt(idx++, strat.getSNR());
        ps.setInt(idx++, strat.getOBNR());
        ps.setString(idx++, strat.getTipus());
        ps.setString(idx++, strat.getAllapot());
        ps.setInt(idx++, strat.isFeltDat() ? 1 : 0);
        ps.setString(idx++, strat.getKor());
        ps.setString(idx++, strat.getKorszak());
        ps.setInt(idx, strat.getId());
        ps.executeUpdate();
        connection.close();

        return findById(strat.getId());
    }

    public Strat findById(int id) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(StratQuery.FIND_BY_ID.getQuery());
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Strat strat = StratMapper.getInstance().map(rs);
        connection.close();
        return strat;
    }

    public List<Strat> findByAsatasId(int asatasId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(StratQuery.FIND_ALL_FOR_ASATAS.getQuery());
        ps.setInt(1, asatasId);
        ResultSet rs = ps.executeQuery();
        List<Strat> strats = new ArrayList<Strat>();
        while (rs.next()) {
            Strat strat = StratMapper.getInstance().map(rs);
            strats.add(strat);
        }

        connection.close();
        return strats;
    }

    public List<String> findAttributeList(String attribute) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = StratQuery.FIND_ALL_ATTRIBUTE.getQuery()
                .replace("{col}", attribute);
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<String> strings = StratMapper.getInstance().mapAttribute(attribute, rs);
        connection.close();
        return strings;
    }
}
