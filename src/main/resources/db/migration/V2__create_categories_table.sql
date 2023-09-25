CREATE TABLE categories (
  id SERIAL PRIMARY KEY,
  title varchar NOT NULL UNIQUE
);