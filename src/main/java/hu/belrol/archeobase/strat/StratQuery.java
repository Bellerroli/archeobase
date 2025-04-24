package hu.belrol.archeobase.strat;

public enum StratQuery {
    INSERT_FULL("INSERT INTO STRAT(SNR, OBNR, tipus, allapot, leiras, felt_dat, asatas_id, kor, korszak) " +
            "VALUES(?,?,?,?,?,?,?,?,?)"),
    INSERT_PARTIAL("INSERT INTO STRAT(SNR, OBNR, kor, tipus, felt_dat, asatas_id) VALUES(?,?,?,?,'0',?)"),
    FIND_ALL_FOR_ASATAS("SELECT * FROM STRAT WHERE asatas_id = ?"),
    FIND_BY_ID("SELECT * FROM STRAT WHERE id = ?"),
    UPDATE("UPDATE STRAT " +
            "SET SNR = ?," +
            "OBNR = ?," +
            "tipus = ?," +
            "allapot = ?," +
            "leiras = ?," +
            "felt_dat = ?," +
            "kor = ?," +
            "korszak = ? " +
            "WHERE id = ?"),
    FIND_ALL_ATTRIBUTE("SELECT DISTINCT {col} FROM STRAT"),
    ;

    private final String query;

    StratQuery(String string) {
        query = string;
    }

    public String getQuery() {
        return query;
    }
}
