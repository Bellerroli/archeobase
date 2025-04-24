package hu.belrol.archeobase.asatas;

public class Asatas {
    private int id;
    private String megnevezes;

    public Asatas(int id, String megnevezes) {
        this.id = id;
        this.megnevezes = megnevezes;
    }

    public Asatas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public void setMegnevezes(String megnevezes) {
        this.megnevezes = megnevezes;
    }
}
