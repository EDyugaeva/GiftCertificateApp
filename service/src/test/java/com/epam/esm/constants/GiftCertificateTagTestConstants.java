package com.epam.esm.constants;

import com.epam.esm.model.GiftCertificateTag;

import java.util.Arrays;
import java.util.List;

public class GiftCertificateTagTestConstants {
    public static final GiftCertificateTag GIFT_TAG_1 = new GiftCertificateTag(1L, 1L, 1L);
    public static final GiftCertificateTag GIFT_TAG_2 = new GiftCertificateTag(2L, 2L, 1L);
    public static final List<GiftCertificateTag> TAG_GIFT_LIST = Arrays.asList(GIFT_TAG_1, GIFT_TAG_2);

}
