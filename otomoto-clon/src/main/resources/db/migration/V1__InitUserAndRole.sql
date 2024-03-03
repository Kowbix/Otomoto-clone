CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255),
   username VARCHAR(255) NOT NULL UNIQUE,
   email VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255)
);

CREATE TABLE users_roles (
     user_id BIGINT,
     role_id BIGINT,
     FOREIGN KEY (user_id) REFERENCES users(id),
     FOREIGN KEY (role_id) REFERENCES roles(id),
     PRIMARY KEY (user_id, role_id)
);
