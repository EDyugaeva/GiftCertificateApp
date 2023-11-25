CREATE TABLE gift_certificate
(
    id               BIGSERIAL PRIMARY KEY,
    name             varchar UNIQUE ,
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
    id   BIGSERIAL PRIMARY KEY,
    gift_id bigint REFERENCES gift_certificate (id) ON UPDATE CASCADE,
    tag_id  bigint REFERENCES tag (id) ON UPDATE CASCADE

)

