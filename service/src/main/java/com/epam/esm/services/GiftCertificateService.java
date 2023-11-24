package com.epam.esm.services;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    GiftCertificate saveGiftCertificate(String name, String description, float price, int duration);
    GiftCertificate updateGiftCertificate(Long id, Map<String, Object> params);
    GiftCertificate getGiftCertificatesById(Long id);
    List<GiftCertificate> getAll();

    List<GiftCertificate> getGiftCertificatesByParameter(String sortedBy, String sortingValue, String orderingBy, boolean desc);
}
