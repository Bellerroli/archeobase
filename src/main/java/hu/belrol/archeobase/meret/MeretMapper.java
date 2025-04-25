package hu.belrol.archeobase.meret;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeretMapper {
    public static List<Meret> mapList(ResultSet rs) throws SQLException {
        List<Meret> merets = new ArrayList<Meret>();
        while (rs.next()) {
            Meret meret = new Meret();
            meret.setId(rs.getInt(rs.findColumn("id")));
            meret.setMeret(rs.getInt(rs.findColumn("meret")));
            meret.setMeretTipus(rs.getString(rs.findColumn("meret_tipus")));
            meret.setMertekegyseg(Mertekegyseg.valueOf(rs.getString(rs.findColumn("mertekegyseg"))));
            meret.setStratId(rs.getInt(rs.findColumn("strat_id")));
            merets.add(meret);
        }
        return merets;
    }
}
