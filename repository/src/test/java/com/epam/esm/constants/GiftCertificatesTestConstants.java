package com.epam.esm.constants;

import com.epam.esm.model.GiftCertificate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.epam.esm.constants.QueryParams.DATE;
import static com.epam.esm.constants.QueryParams.NAME;
import static com.epam.esm.constants.TagTestConstants.*;

public class GiftCertificatesTestConstants {
    public static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1L, "gift_certificate_1", "description 1", 150.5f, 5,
            LocalDateTime.of(2018, 1, 1, 0, 0),
            LocalDateTime.of(2018, 1, 1, 0, 5), Arrays.asList(TAG_1, TAG_2, TAG_3));
    public static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2L, "gift_certificate_2", "description 2", 150.5f, 5,
            LocalDateTime.of(2018, 2, 1, 0, 0),
            LocalDateTime.of(2018, 2, 5, 0, 5), Arrays.asList(TAG_1, TAG_2));
    public static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3L, "gift_certificate_3", "description 3", 140f, 4,
            LocalDateTime.of(2018, 3, 1, 0, 0),
            LocalDateTime.of(2018, 3, 9, 0, 5), null);
    public static final GiftCertificate GIFT_CERTIFICATE_4 = new GiftCertificate(4L, "gift_certificate_4", "description 4", 200f, 7,
            LocalDateTime.of(2018, 4, 1, 0, 0),
            LocalDateTime.of(2018, 4, 10, 0, 5), null);
    public static final GiftCertificate GIFT_CERTIFICATE_5 = new GiftCertificate(5L, "gift_certificate_5", "description 5", 500f, 10,
            LocalDateTime.of(2018, 5, 1, 0, 0),
            LocalDateTime.of(2018, 5, 11, 0, 5), null);
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_4, GIFT_CERTIFICATE_5);
    public static final GiftCertificate NEW_GIFT_CERTIFICATE = new GiftCertificate("new_gift_certificate", "description new certificate", 300f, 5,
            LocalDateTime.of(2023, 1, 1, 0, 0),
            LocalDateTime.of(2023, 1, 1, 0, 5), null);

    public static final GiftCertificate UPDATED_CERTIFICATE = new GiftCertificate(3L, "UPDATED_gift_certificate", "description update", 300f, 5,
            LocalDateTime.of(2023, 1, 1, 0, 0),
            LocalDateTime.of(2023, 1, 1, 0, 5), null);
    public static final Long NEW_ID = 6L;
    public static final Long ABSENT_ID = 7L;
    public static final String TAG_NAME_VALUE = "tag_name_2";
    public static final String NAME_VALUE = "gift_certificate_2";
    public static final String DESCRIPTION_VALUE = "description";
    public static final String DESC = "desc";
    public static final String ASC = "asc";
    public static final String TAG_NAME_IN_QUERY = "tag.name";
    public static final String DATA_IN_QUERY = "create_date";
    public static final String ORDER_BY = "ORDER BY";
    public static final List<String> SORTING_VALUE = Arrays.asList(NAME, DATE.concat(DESC));
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST_WITH_TAG_NAME = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2);

    public static final String QUERY = "WHERE  gc.id in (SELECT gc.id " +
            "FROM gift_certificate gc" +
            "         JOIN gift_certificate_tag gct ON gc.id = gct.gift_id" +
            "         JOIN tag ON gct.tag_id = tag.id " +
            " WHERE tag.name = 'tag_name_2') " +
            " AND " +
            "gc.name ILIKE '%gift_certificate_2%' " + " AND " + "gc.description ILIKE '%description%'" + " ORDER BY " + "gc.name, create_date";
}
