package com.epam.esm.dao;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate);
    GiftCertificate getGiftCertificate(long id) throws DataNotFoundException;
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getGiftCertificates();
    List<GiftCertificate> getGiftCertificatesByQuery(String query);
    void deleteGiftCertificate(long id);
}
