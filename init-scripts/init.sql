CREATE TABLE gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             varchar UNIQUE,
    description      varchar,
    price            float,
    duration         int,
    create_date      timestamp,
    last_update_date timestamp

);

CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY,
    name varchar UNIQUE
);

CREATE TABLE gift_certificate_tag
(
    id      SERIAL PRIMARY KEY,
    gift_id bigint REFERENCES gift_certificate (id) ON UPDATE CASCADE ON delete cascade,
    tag_id  bigint REFERENCES tag (id) ON UPDATE CASCADE ON delete cascade,
    CONSTRAINT unique_pair_constraint unique (gift_id, tag_id)
);

CREATE TABLE Users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL
);

CREATE TABLE Orders
(
    id                  SERIAL PRIMARY KEY,
    created_time        TIMESTAMP NOT NULL,
    price               FLOAT     NOT NULL,
    user_id             INTEGER REFERENCES Users (id),
    gift_certificate_id INTEGER REFERENCES Gift_Certificate (id)
);

INSERT INTO tag (name)
SELECT 'Tag ' || generate_series(1, 1000)::text;

INSERT INTO users (first_name, last_name, email, password)
SELECT
    'First' || generate_series(1, 1000)::text,
    'Last' || generate_series(1, 1000)::text,
    'user' || generate_series(1, 1000)::text || '@example.com',
    'password' || generate_series(1, 1000)::text;

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
SELECT
    'Gift Certificate' || generate_series(1, 10000)::text,
    'Description ' || generate_series(1, 10000)::text,
    random() * 100.0,
    random() * 30 + 1,
    current_date - (random() * 365)::integer,
    current_date - (random() * 365)::integer;

INSERT INTO gift_certificate_tag (gift_id, tag_id)
SELECT
    generate_series(1, 10000)::bigint,
    generate_series(1, 1000)::bigint;


