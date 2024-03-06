CREATE TABLE IF NOT EXISTS engines (
   id SERIAL PRIMARY KEY,
   capacity INT NOT NULL,
   horsepower INT NOT NULL
);


CREATE TABLE if not exists cars (
    id SERIAL PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    model_id BIGINT NOT NULL,
    generation_id BIGINT,
    year_production INTEGER,
    bodywork_type_id BIGINT NOT NULL,
    fuel_id BIGINT NOT NULL,
    millage INTEGER,
    damage_condition_id BIGINT NOT NULL,
    vin VARCHAR(255),
    engine_id BIGINT,
    transmission_id BIGINT NOT NULL,
    door_count INTEGER,
    seat_count INTEGER,
    FOREIGN KEY (brand_id) REFERENCES brands(id),
    FOREIGN KEY (model_id) REFERENCES models(id),
    FOREIGN KEY (generation_id) REFERENCES generations(id),
    FOREIGN KEY (bodywork_type_id) REFERENCES bodywork_types(id),
    FOREIGN KEY (fuel_id) REFERENCES fuel(id),
    FOREIGN KEY (damage_condition_id) REFERENCES damage_conditions(id),
    FOREIGN KEY (engine_id) REFERENCES engines(id),
    FOREIGN KEY (transmission_id) REFERENCES transmissions(id)
);

CREATE TABLE if not exists images (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    car_id INT REFERENCES cars(id)
);
