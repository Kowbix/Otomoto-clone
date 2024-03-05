create table if not exists brands (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

create table if not exists models (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand_id BIGINT,
    foreign key (brand_id) references brands(id)
);

CREATE TABLE IF NOT EXISTS generations (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    model_id BIGINT,
    start_production_year INT,
    end_production_year INT,
    FOREIGN KEY (model_id) REFERENCES models(id)
);