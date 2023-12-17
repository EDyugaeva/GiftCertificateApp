CREATE TABLE gift_certificate
(
    id               BIGSERIAL PRIMARY KEY,
    name             varchar UNIQUE,
    description      varchar,
    price            float,
    duration         int,
    create_date      timestamp,
    last_update_date timestamp

);

CREATE TABLE tag
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar UNIQUE
);

CREATE TABLE gift_certificate_tag
(
    id      BIGSERIAL PRIMARY KEY,
    gift_id bigint REFERENCES gift_certificate (id) ON UPDATE CASCADE ON delete cascade,
    tag_id  bigint REFERENCES tag (id) ON UPDATE CASCADE ON delete cascade,
    CONSTRAINT unique_pair_constraint unique (gift_id, tag_id)
);

CREATE TABLE User
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL
);

CREATE TABLE Order
(
    id                  SERIAL PRIMARY KEY,
    created_time        TIMESTAMP NOT NULL,
    price               FLOAT     NOT NULL,
    user_id             INTEGER REFERENCES User (id),
    gift_certificate_id INTEGER REFERENCES Gift_Certificate (id)
);

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('GiftCert1', 'Description 1', 25.99, 30, CURRENT_TIMESTAMP, null),
       ('GiftCert2', 'Description 2', 19.99, 20, CURRENT_TIMESTAMP, null),
       ('GiftCert3', 'Description 3', 35.50, 45, CURRENT_TIMESTAMP, null),
       ('GiftCert4', 'Description 4', 15.75, 15, CURRENT_TIMESTAMP, null),
       ('GiftCert5', 'Description 5', 50.00, 60, CURRENT_TIMESTAMP, null),
       ('GiftCert6', 'Description 6', 30.25, 25, CURRENT_TIMESTAMP, null),
       ('GiftCert7', 'Description 7', 40.99, 50, CURRENT_TIMESTAMP, null),
       ('GiftCert8', 'Description 8', 22.50, 40, CURRENT_TIMESTAMP, null),
       ('GiftCert9', 'Description 9', 18.00, 30, CURRENT_TIMESTAMP, null),
       ('GiftCert10', 'Description 10', 45.75, 55, CURRENT_TIMESTAMP, null);

-- Inserting data into the tag table
INSERT INTO tag (name)
VALUES ('Tag1'),
       ('Tag2'),
       ('Tag3'),
       ('Tag4'),
       ('Tag5'),
       ('Tag6'),
       ('Tag7'),
       ('Tag8'),
       ('Tag9'),
       ('Tag10');

-- Inserting data into the gift_certificate_tag table
INSERT INTO gift_certificate_tag (gift_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (5, 9),
       (5, 10),
       (2, 1),
       (2, 2),
       (3, 3),
       (3, 4),
       (4, 5),
       (4, 6),
       (5, 7),
       (5, 8),
       (1, 9),
       (1, 10);

