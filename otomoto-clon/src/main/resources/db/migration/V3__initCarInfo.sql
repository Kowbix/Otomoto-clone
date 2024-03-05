create table if not exists bodywork_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table if not exists bodyworks (
    id SERIAL PRIMARY KEY,
    doors_count int,
    seats_count int,
    bodywork_type_id BIGINT,
    foreign key (bodywork_type_id) references bodywork_types(id)
);

create table if not exists fueals (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table if not exists damage_conditions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table if not exists images (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL
);

create table if not exists transmissions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table if not exists engines (
    id SERIAL PRIMARY KEY,
    capacity int,
    horsepower int,
    transmission_id BIGINT,
    foreign key(transmission_id) references transmissions(id)
);