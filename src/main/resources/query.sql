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

