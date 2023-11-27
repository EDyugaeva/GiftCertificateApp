package com.epam.esm.utils;

import com.epam.esm.model.GiftCertificate;

import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.constants.QueryParams.*;

public class GiftCertificateParamsCreator {

    public Map<String, Object> getActualParams(GiftCertificate giftCertificate) {
        Map<String, Object> params = new HashMap<>();
        if (giftCertificate.getName() != null) {
            params.put(NAME, giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            params.put(DESCRIPTION, giftCertificate.getDescription());
        }
        if (giftCertificate.getDuration() != 0) {
            params.put(DURATION, giftCertificate.getDuration());
        }
        if (giftCertificate.getPrice() != 0) {
            params.put(PRICE, giftCertificate.getPrice());
        }
        if (giftCertificate.getTagList() != null) {
            params.put(TAGS,giftCertificate.getTagList());
        }
        return params;
    }
}
