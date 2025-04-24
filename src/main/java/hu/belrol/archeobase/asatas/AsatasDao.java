package hu.belrol.archeobase.asatas;

import hu.belrol.archeobase.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsatasDao {
    private static AsatasDao instance;

    private AsatasDao() {
    }

    public static AsatasDao getInstance() {
        if (instance == null) {
            instance = new AsatasDao();
        }
        return instance;
    }

    public Asatas create(final Asatas asatas) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(AsatasQuery.INSERT.getQuery(), Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, asatas.getMegnevezes());
        ps.executeUpdate();
        asatas.setId(ps.getGeneratedKeys().getInt(1));
        connection.close();
        return asatas;
    }

    public List<Asatas> getAll() throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(AsatasQuery.QUERY_ALL.getQuery());
        List<Asatas> asatasok = new ArrayList<>();
        while (resultSet.next()) {
            asatasok.add(AsatasMapper.getInstance().map(resultSet));
        }
        connection.close();
        return asatasok;
    }

    public Asatas findById(final int id) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement ps = connection.prepareStatement(AsatasQuery.FIND_BY_ID.getQuery());
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        Asatas map = AsatasMapper.getInstance().map(resultSet);
        connection.close();
        return map;
    }
}
