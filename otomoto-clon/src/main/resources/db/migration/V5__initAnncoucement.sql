CREATE TABLE if not exists voivodeship (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE if not exists locations (
    id SERIAL PRIMARY KEY,
    city_name VARCHAR(255) NOT NULL,
    voivodeship_id BIGINT NOT NULL,
    FOREIGN KEY (voivodeship_id) REFERENCES voivodeship(id)
);

CREATE TABLE announcements (
    id SERIAL PRIMARY KEY,
    car_id BIGINT NOT NULL,
    description_url VARCHAR(255),
    user_id BIGINT NOT NULL,
    added_date DATE,
    views BIGINT DEFAULT 0,
    price FLOAT,
    location_id BIGINT NOT NULL,
    main_image_id BIGINT,
    is_active BOOLEAN DEFAULT true,
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (location_id) REFERENCES locations(id),
    FOREIGN KEY (main_image_id) REFERENCES images(id)
);