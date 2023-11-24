CREATE TABLE gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             varchar,
    description      varchar,
    price            float,
    duration         int,
    create_date      timestamp,
    last_update_date timestamp

);

CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY,
    name varchar
);

CREATE TABLE gift_certificate_tag
(
    id   SERIAL PRIMARY KEY,
    gift_id bigint REFERENCES gift_certificate (id) ON UPDATE CASCADE,
    tag_id  bigint REFERENCES tag (id) ON UPDATE CASCADE

)

