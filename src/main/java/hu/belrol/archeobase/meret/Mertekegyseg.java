package hu.belrol.archeobase.meret;

public enum Mertekegyseg {
    CM("cm"),
    M("m");

    private final String name;

    Mertekegyseg(String m) {
        name = m;
    }

    public String getName() {
        return name;
    }
}
