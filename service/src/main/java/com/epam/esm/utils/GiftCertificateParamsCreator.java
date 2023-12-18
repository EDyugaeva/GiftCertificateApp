package com.epam.esm.utils;

import com.epam.esm.model.GiftCertificate;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.constants.Constants.GiftCertificateColumn.*;
import static com.epam.esm.constants.Constants.NAME;
import static com.epam.esm.constants.Constants.TAGS;
@Slf4j
public class GiftCertificateParamsCreator {

    public Map<String, Object> getActualParams(GiftCertificate giftCertificate) {
        log.info("Creating map for gift certificate parameters {}", giftCertificate);
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
        if (giftCertificate.getTagSet() != null) {
            params.put(TAGS,giftCertificate.getTagSet());
        }
        return params;
    }
}
