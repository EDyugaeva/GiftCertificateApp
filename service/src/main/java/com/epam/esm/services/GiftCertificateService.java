package com.epam.esm.services;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate);

    GiftCertificate getGiftCertificatesById(Long id);

    List<GiftCertificate> getAll();

    void deleteGiftCertificate(long id);

    List<GiftCertificate> getGiftCertificatesByParameter(Map<String, String> filteredBy,
                                                         List<String> orderingBy,
                                                         String order);
}
