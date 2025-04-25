package hu.belrol.archeobase.meret;

public class Meret {
    private int id;
    private int meret;
    private String meretTipus;
    private Mertekegyseg mertekegyseg;
    private int stratId;

    public Meret(int id, int meret, String meretTipus, Mertekegyseg mertekegyseg, int stratId) {
        this.id = id;
        this.meret = meret;
        this.meretTipus = meretTipus;
        this.mertekegyseg = mertekegyseg;
        this.stratId = stratId;
    }

    public Meret() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeret() {
        return meret;
    }

    public void setMeret(int meret) {
        this.meret = meret;
    }

    public String getMeretTipus() {
        return meretTipus;
    }

    public void setMeretTipus(String meretTipus) {
        this.meretTipus = meretTipus;
    }

    public Mertekegyseg getMertekegyseg() {
        return mertekegyseg;
    }

    public void setMertekegyseg(Mertekegyseg mertekegyseg) {
        this.mertekegyseg = mertekegyseg;
    }

    public int getStratId() {
        return stratId;
    }

    public void setStratId(int stratId) {
        this.stratId = stratId;
    }

    @Override
    public String toString() {
        return meretTipus + ": " + meret + " " + mertekegyseg;
    }
}
