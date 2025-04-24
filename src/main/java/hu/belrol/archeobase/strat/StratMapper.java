package hu.belrol.archeobase.strat;

import hu.belrol.archeobase.meret.MeretController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StratMapper {
    private static StratMapper instance;

    private StratMapper() {
    }

    public static StratMapper getInstance() {
        if (instance == null) {
            instance = new StratMapper();
        }
        return instance;
    }

    public Strat map(ResultSet rs) throws SQLException {
        Strat strat = new Strat();
        strat.setId(rs.getInt(rs.findColumn("id")));
        strat.setSNR(rs.getInt(rs.findColumn("SNR")));
        strat.setOBNR(rs.getInt(rs.findColumn("OBNR")));
        strat.setTipus(rs.getString(rs.findColumn("tipus")));
        strat.setAllapot(rs.getString(rs.findColumn("allapot")));
        strat.setLeiras(rs.getString(rs.findColumn("leiras")));
        strat.setFeltDat(rs.getInt(rs.findColumn("felt_dat")) == 1);
        strat.setAsatasId(rs.getInt(rs.findColumn("asatas_id")));
        strat.setKor(rs.getString(rs.findColumn("kor")));
        strat.setKorszak(rs.getString(rs.findColumn("korszak")));

        return strat;
    }

    public List<String> mapAttribute(String attribute, ResultSet rs) throws SQLException {
        List<String> list = new ArrayList<String>();
        while (rs.next()) {
            list.add(rs.getString(attribute));
        }
        return list;
    }

    public StratDto toDto(Strat strat) throws SQLException {
        StratDto stratDto = new StratDto();
        stratDto.setId(strat.getId());
        stratDto.setSNR(strat.getSNR());
        stratDto.setOBNR(strat.getOBNR());
        stratDto.setTipus(strat.getTipus());
        stratDto.setAllapot(strat.getAllapot());
        stratDto.setLeiras(strat.getLeiras());
        stratDto.setAsatasId(strat.getAsatasId());
        stratDto.setFeltDat(strat.isFeltDat());
        stratDto.setKor(strat.getKor());
        stratDto.setKorszak(strat.getKorszak());
        stratDto.setMeretList(MeretController.getInstance().getAll(strat.getId()));
        return stratDto;
    }

    public Strat toStrat(StratDto stratDto) throws SQLException {
        Strat strat = new Strat();
        strat.setId(stratDto.getId());
        strat.setSNR(stratDto.getSNR());
        strat.setOBNR(stratDto.getOBNR());
        strat.setTipus(stratDto.getTipus());
        strat.setAllapot(stratDto.getAllapot());
        strat.setLeiras(stratDto.getLeiras());
        strat.setAsatasId(stratDto.getAsatasId());
        strat.setFeltDat(stratDto.isFeltDat());
        strat.setKor(stratDto.getKor());
        strat.setKorszak(stratDto.getKorszak());
        return strat;
    }
}
