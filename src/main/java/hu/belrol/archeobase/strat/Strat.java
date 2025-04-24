package hu.belrol.archeobase.strat;

public class Strat {
    private int id;
    private int SNR;
    private int OBNR;
    private String tipus;
    private String allapot;
    private String leiras;
    private boolean feltDat;
    private String kor;
    private String korszak;
    private int asatasId;

    public Strat(int id, int SNR, int OBNR, String tipus, String allapot, String leiras, boolean feltDat, String kor, String korszak, int asatasId) {
        this.id = id;
        this.SNR = SNR;
        this.OBNR = OBNR;
        this.tipus = tipus;
        this.allapot = allapot;
        this.leiras = leiras;
        this.feltDat = feltDat;
        this.kor = kor;
        this.korszak = korszak;
        this.asatasId = asatasId;
    }

    public Strat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSNR() {
        return SNR;
    }

    public void setSNR(int SNR) {
        this.SNR = SNR;
    }

    public int getOBNR() {
        return OBNR;
    }

    public void setOBNR(int OBNR) {
        this.OBNR = OBNR;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getAllapot() {
        return allapot;
    }

    public void setAllapot(String allapot) {
        this.allapot = allapot;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public boolean isFeltDat() {
        return feltDat;
    }

    public void setFeltDat(boolean feltDat) {
        this.feltDat = feltDat;
    }

    public String getKor() {
        return kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public String getKorszak() {
        return korszak;
    }

    public void setKorszak(String korszak) {
        this.korszak = korszak;
    }

    public int getAsatasId() {
        return asatasId;
    }

    public void setAsatasId(int asatasId) {
        this.asatasId = asatasId;
    }
}
