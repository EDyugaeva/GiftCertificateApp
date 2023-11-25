package com.epam.esm.services;

import com.epam.esm.exceptions.TestException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate updateGiftCertificate(Long id, Map<String, Object> params);

    GiftCertificate getGiftCertificatesById(Long id);

    List<GiftCertificate> getAll() throws TestException;

    void deleteGiftCertificate(long id);

    List<GiftCertificate> getGiftCertificatesByParameter(Map<String, String> filteredBy,
                                                         List<String> orderingBy,
                                                         String order) throws TestException;
}
