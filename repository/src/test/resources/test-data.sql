insert INTO TAG (id, name) VALUES (1, 'tag_name_1');
insert INTO TAG (id, name) VALUES (2, 'tag_name_2');
insert INTO TAG (id, name) VALUES (3, 'tag_name_3');
insert INTO TAG (id, name) VALUES (4, 'tag_name_4');
insert INTO TAG (id, name) VALUES (5, 'tag_name_5');

INSERT INTO GIFT_CERTIFICATE (id, NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) values ( 1, 'gift_certificate_1','description 1', 150.5 , 5, '2018-01-01 00:00:00', '2018-01-01 00:05:00' );
INSERT INTO GIFT_CERTIFICATE (id, NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) values ( 2, 'gift_certificate_2','description 2', 150.5 , 5, '2018-02-01 00:00:00', '2018-02-05 00:05:00' );
INSERT INTO GIFT_CERTIFICATE (id, NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) values ( 3, 'gift_certificate_3','description 3', 140 , 4, '2018-03-01 00:00:00', '2018-03-09 00:05:00' );
INSERT INTO GIFT_CERTIFICATE (id, NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) values ( 4, 'gift_certificate_4','description 4', 200 , 7, '2018-04-01 00:00:00', '2018-04-10 00:05:00' );
INSERT INTO GIFT_CERTIFICATE (id, NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) values ( 5, 'gift_certificate_5','description 5', 500 , 10, '2018-05-01 00:00:00', '2018-05-11 00:05:00' );

INSERT INTO GIFT_CERTIFICATE_TAG (id, gift_id, tag_id) values ( 1, 1, 1 );
INSERT INTO GIFT_CERTIFICATE_TAG (id, gift_id, tag_id) values ( 2, 1, 2 );
INSERT INTO GIFT_CERTIFICATE_TAG (id, gift_id, tag_id) values ( 3, 1, 3 );

INSERT INTO GIFT_CERTIFICATE_TAG (id, gift_id, tag_id) values ( 4, 2, 1 );
INSERT INTO GIFT_CERTIFICATE_TAG (id, gift_id, tag_id) values ( 5, 2, 2 );

