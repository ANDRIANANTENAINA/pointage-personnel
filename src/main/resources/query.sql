-- create tables for postgresql
create table if not exists employe
(
    num_empl serial
        primary key,
    nom      varchar(100),
    prenom   varchar(100),
    poste    varchar(100),
    salaire  integer
);

create table if not exists conge
(
    num_conge    serial
        primary key,
    num_empl     integer not null
        references employe
            on delete cascade,
    motif        varchar(255),
    nbjr         integer,
    date_demande timestamp default CURRENT_TIMESTAMP,
    date_retour  timestamp default CURRENT_TIMESTAMP
);

create table if not exists pointage
(
    num_pointage    serial
        primary key,
    num_empl     integer not null
        references employe
    on delete cascade,
    pointage        varchar(50),
    date_pointage  timestamp default CURRENT_TIMESTAMP
    );




-- create tables for mysql
CREATE TABLE IF NOT EXISTS employe (
    num_empl SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    poste VARCHAR(100),
    salaire INTEGER
    );

CREATE TABLE IF NOT EXISTS conge (
    num_conge SERIAL PRIMARY KEY,
    num_empl INTEGER NOT NULL REFERENCES employe (num_empl) ON DELETE CASCADE,
    motif VARCHAR(255),
    nbjr INTEGER,
    date_demande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_retour TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS pointage (
    num_pointage SERIAL PRIMARY KEY,
    num_empl INTEGER NOT NULL REFERENCES employe (num_empl) ON DELETE CASCADE,
    pointage VARCHAR(50),
    date_pointage TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
