package hu.belrol.archeobase.asatas;

public enum AsatasQuery {
    INSERT("INSERT INTO ASATAS(megnevezes) VALUES(?)"),
    QUERY_ALL("SELECT * FROM ASATAS"),
    FIND_BY_ID("SELECT * FROM ASATAS WHERE id = ?"),
    ;

    private final String query;

    AsatasQuery(String s) {
        query = s;
    }

    public String getQuery() {
        return query;
    }
}
