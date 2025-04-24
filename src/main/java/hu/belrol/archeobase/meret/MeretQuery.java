package hu.belrol.archeobase.meret;

public enum MeretQuery {
    INSERT("INSERT INTO MERET(meret, meret_tipus, mertekegyseg, strat_id) VALUES (?, ?, ?, ?)"),
    FIND_BY_STRATID("SELECT * FROM MERET WHERE strat_id = ?"),
    FIND_ALL_ATTRIBUTE("SELECT DISTINCT {col} FROM MERET"),
    DELETE_BY_STARTID("DELETE FROM MERET WHERE strat_id = ?"),
    ;

    private final String query;

    MeretQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
