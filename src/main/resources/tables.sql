CREATE TABLE Zones (
   id SERIAL PRIMARY KEY,
   locZn TEXT
);

CREATE TABLE Locations (
   loc INTEGER PRIMARY KEY,
   locName TEXT,
   locZn INT REFERENCES Zones(id) NOT NULL
);

CREATE TYPE loc_purp_desc_types AS ENUM('MQ', 'M2');
CREATE TYPE loc_qty_types AS ENUM('RPQ', 'DPQ');
CREATE TYPE flow_ind_types AS ENUM('R', 'D');

CREATE TABLE Facilities (
    loc INT REFERENCES Locations(loc) NOT NULL,
    loc_purpose_desc loc_purp_desc_types,
    loc_QTI loc_qty_types,
    flow_ind flow_ind_types,
    dc INT,
    opc INT,
    tsq INT,
    oac INT,
    IT BooL,
    auth_overrun_ind BOOL,
    nom_cap_exceed_ind BOOL,
    all_qty_available BOOL,
    qty_reason TEXT,
    PRIMARY KEY (loc, loc_purpose_desc)
);
