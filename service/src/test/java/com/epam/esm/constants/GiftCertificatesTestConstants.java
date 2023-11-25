package com.epam.esm.constants;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.epam.esm.constants.TagTestConstants.*;

public class GiftCertificatesTestConstants {
    public static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1L, "gift_certificate_1", "description 1",  150.5f, 5,
            LocalDateTime.of(2018, 1, 1, 0, 0),
           null, Arrays.asList(TAG_1, TAG_2));
    public static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2L, "gift_certificate_2", "description 2",150.5f, 5,
            LocalDateTime.of(2018, 2, 1, 0, 0),
            LocalDateTime.of(2018, 2, 5, 0, 5), Arrays.asList(TAG_1, TAG_2));
    public static final GiftCertificate GIFT_CERTIFICATE_2_BEFORE_UPDATE = new GiftCertificate(2L, "gift_certificate_2_before", "description 2",150.5f, 5,
            LocalDateTime.of(2018, 2, 1, 0, 0),
            null, Arrays.asList(TAG_1, TAG_2));
    public static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3L, "gift_certificate_3", "description 3", 140f, 4,
            LocalDateTime.of(2018, 3, 1, 0, 0),
            LocalDateTime.of(2018, 3, 9, 0, 5), null);
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
    public static final String TAG_NAME_VALUE = "tag_name";
    public static final String TAG_NAME_IN_QUERY = "tag.name";
    public static final String NAME_VALUE = "gift_certificate";
    public static final String DATA_IN_QUERY = "create_date";
    public static final String DESC = "desc";
    public static final String ASC = "asc";
    public static final String DESCRIPTION_VALUE = "description_value";
    public static final List<Tag> TAG_LIST = Arrays.asList(TAG_1, TAG_2);
}
