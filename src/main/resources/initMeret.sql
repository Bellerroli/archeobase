CREATE TABLE IF NOT EXISTS MERET
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
    meret        INTEGER,
    meret_tipus  TEXT,
    mertekegyseg TEXT,
    strat_id     INTEGER,
    FOREIGN KEY (strat_id) REFERENCES STRAT (id)
);