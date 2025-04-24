package hu.belrol.archeobase.asatas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AsatasMapper {
    private static AsatasMapper instance;

    private AsatasMapper() {
    }

    public Asatas map(ResultSet rs) throws SQLException {
        Asatas asatas = new Asatas();
        asatas.setId(rs.getInt(rs.findColumn("id")));
        asatas.setMegnevezes(rs.getString(rs.findColumn("megnevezes")));
        return asatas;
    }

    public static AsatasMapper getInstance() {
        if (instance == null) {
            instance = new AsatasMapper();
        }
        return instance;
    }
}
