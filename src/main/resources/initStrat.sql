CREATE TABLE IF NOT EXISTS STRAT
(
    id           INTEGER PRIMARY KEY UNIQUE,
    SNR          INTEGER NOT NULL,
    OBNR         INTEGER,
    tipus        TEXT,
    allapot      TEXT,
    leiras       TEXT,
    meret        REAL,
    meret_tipus  TEXT,
    mertekegyseg TEXT,
    kor          TEXT,
    korszak      TEXT,
    felt_dat     INTEGER NOT NULL,
    asatas_id    INTEGER,
    FOREIGN KEY (asatas_id) REFERENCES ASATAS (id)
    );