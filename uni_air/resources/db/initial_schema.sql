BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "AIRLINE"
(
    "IATA_CODE" INTEGER NOT NULL UNIQUE,
    "NAME"      INTEGER NOT NULL,
    PRIMARY KEY ("IATA_CODE")
);
CREATE TABLE IF NOT EXISTS "AIRPORT"
(
    "IATA_CODE" TEXT NOT NULL UNIQUE,
    "FULL NAME" TEXT NOT NULL,
    PRIMARY KEY ("IATA_CODE")
);
CREATE TABLE IF NOT EXISTS "BOOKING"
(
    "ID"       INTEGER NOT NULL UNIQUE,
    "CUSTOMER" INTEGER NOT NULL,
    "FLIGHT"   INTEGER NOT NULL,
    "USER"     INTEGER NOT NULL,
    PRIMARY KEY ("ID"),
    FOREIGN KEY ("FLIGHT") REFERENCES "FLIGHT" ("ID"),
    FOREIGN KEY ("USER") REFERENCES "USER" ("DNI")
);
CREATE TABLE IF NOT EXISTS "CHECK_IN"
(
    "ID"      INTEGER NOT NULL UNIQUE,
    "BOOKING" INTEGER NOT NULL,
    "SEAT"    TEXT    NOT NULL,
    PRIMARY KEY ("ID"),
    FOREIGN KEY ("BOOKING") REFERENCES "BOOKING" ("ID")
);
CREATE TABLE IF NOT EXISTS "EXTRA"
(
    "ID"      INTEGER NOT NULL UNIQUE,
    "DESC"    TEXT,
    "PRICE"   INTEGER NOT NULL DEFAULT 0,
    "BOOKING" INTEGER NOT NULL,
    "TYPE"    INTEGER NOT NULL,
    PRIMARY KEY ("ID"),
    FOREIGN KEY ("BOOKING") REFERENCES "BOOKING" ("ID"),
    FOREIGN KEY ("TYPE") REFERENCES "EXTRA_TYPE" ("ID")
);
CREATE TABLE IF NOT EXISTS "EXTRA_TYPE"
(
    "ID"   INTEGER NOT NULL UNIQUE,
    "NAME" TEXT    NOT NULL,
    "DESC" TEXT,
    PRIMARY KEY ("ID")
);
CREATE TABLE IF NOT EXISTS "FLIGHT"
(
    "ID"             INTEGER NOT NULL UNIQUE,
    "ORIGIN_AIRPORT" TEXT    NOT NULL,
    "DEST_AIRPORT"   TEXT    NOT NULL,
    "AIRLINE_CODE"   TEXT    NOT NULL,
    PRIMARY KEY ("ID"),
    CONSTRAINT "AIRLINE IATA CODE" FOREIGN KEY ("AIRLINE_CODE") REFERENCES "AIRLINE" ("IATA_CODE"),
    CONSTRAINT "DESTINATION AIRPORT" FOREIGN KEY ("DEST_AIRPORT") REFERENCES "AIRPORT" ("IATA_CODE"),
    CONSTRAINT "ORIGIN AIRPORT" FOREIGN KEY ("ORIGIN_AIRPORT") REFERENCES "AIRPORT" ("IATA_CODE")
);
CREATE TABLE IF NOT EXISTS "USER"
(
    "DNI"       INTEGER NOT NULL UNIQUE,
    "NAME"      TEXT    NOT NULL,
    "SURNAME"   TEXT,
    "EMAIL"     TEXT    NOT NULL,
    "BIRTHDATE" INTEGER,
    "USER_TYPE" INTEGER NOT NULL,
    PRIMARY KEY ("DNI"),
    FOREIGN KEY ("USER_TYPE") REFERENCES "USER_TYPE" ("ID"),
    CHECK (date("BIRTHDATE", 'unixepoch') < date('now'))
);
CREATE TABLE IF NOT EXISTS "USER_TYPE"
(
    "ID"   INTEGER NOT NULL UNIQUE,
    "DESC" TEXT    NOT NULL,
    PRIMARY KEY ("ID")
);
COMMIT;