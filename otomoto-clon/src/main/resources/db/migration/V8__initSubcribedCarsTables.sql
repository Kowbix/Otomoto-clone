CREATE TABLE if not exists subscribed_cars (
    id SERIAL PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    model_id BIGINT NOT NULL,
    generation_id BIGINT,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brands(id),
    FOREIGN KEY (model_id) REFERENCES models(id),
    FOREIGN KEY (generation_id) REFERENCES generations(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);