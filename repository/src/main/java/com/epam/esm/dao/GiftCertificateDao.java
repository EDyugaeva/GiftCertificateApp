package com.epam.esm.dao;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate);
    GiftCertificate getGiftCertificate(long id) throws DataNotFoundException;
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getGiftCertificates();
    List<GiftCertificate> getGiftCertificatesByQuery(Map<String, String> filteredBy, List<String> orderingBy, String order);
    void deleteGiftCertificate(long id);
}
