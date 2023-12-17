DROP TABLE IF EXISTS gift_certificate_tag;
DROP TABLE IF EXISTS gift_certificate;
DROP TABLE IF EXISTS tag;


CREATE TABLE gift_certificate
(
    id               bigint PRIMARY KEY auto_increment,
    name             varchar,
    description      varchar,
    price            float,
    duration         int,
    create_date      timestamp,
    last_update_date timestamp

);

CREATE TABLE tag
(
    id   bigint PRIMARY KEY auto_increment,
    name varchar
);

CREATE TABLE gift_certificate_tag
(
    id   bigint PRIMARY KEY auto_increment,
    gift_id bigint REFERENCES gift_certificate (id) ON UPDATE CASCADE ON DELETE CASCADE,
    tag_id  bigint REFERENCES tag (id) ON UPDATE CASCADE ON DELETE CASCADE
);

