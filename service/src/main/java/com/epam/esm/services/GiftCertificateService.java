package com.epam.esm.services;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate);

    GiftCertificate getGiftCertificatesById(Long id);

    List<GiftCertificate> getAll(int page, int size);

    void deleteGiftCertificate(long id);

    GiftCertificate updateGiftCertificateDuration(Long id, int duration);

    GiftCertificate updateGiftCertificatePrice(Long id, float price);

    List<GiftCertificate> getGiftCertificatesByParameters(int page, int size, String name, String description, Optional<String> tagName, String[] sort);

    List<GiftCertificate> findByTagNames(List<String> tagNames, int page, int size);
}
