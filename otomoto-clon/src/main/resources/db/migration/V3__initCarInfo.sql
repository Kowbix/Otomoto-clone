create table if not exists bodywork_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table if not exists fuel (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table if not exists damage_conditions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table if not exists transmissions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
