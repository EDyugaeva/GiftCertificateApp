package com.epam.esm.constants;

import com.epam.esm.model.GiftCertificateTag;

import java.util.Arrays;
import java.util.List;

public class GiftCertificateTagTestConstants {
    public static final GiftCertificateTag GIFT_TAG_1 = new GiftCertificateTag(1L, 1L, 1L);
    public static final GiftCertificateTag GIFT_TAG_2 = new GiftCertificateTag(2L, 2L, 1L);
    public static final GiftCertificateTag GIFT_TAG_3 = new GiftCertificateTag(3L, 3L, 1L);
    public static final GiftCertificateTag GIFT_TAG_4 = new GiftCertificateTag(4L, 1L, 2L);
    public static final GiftCertificateTag GIFT_TAG_5 = new GiftCertificateTag(5L, 2L, 2L);
    public static final List<GiftCertificateTag> TAG_GIFT_LIST = Arrays.asList(GIFT_TAG_1, GIFT_TAG_2, GIFT_TAG_3, GIFT_TAG_4, GIFT_TAG_5);
    public static final GiftCertificateTag GIFT_TAG_NEW = new GiftCertificateTag(3L, 3L);
    public static final Long NEW_ID = 6L;
    public static final Long ABSENT_ID = 7L;
}
