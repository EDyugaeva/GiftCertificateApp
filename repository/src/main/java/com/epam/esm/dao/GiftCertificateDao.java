package com.epam.esm.dao;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate);
    GiftCertificate getGiftCertificate(long id) throws DataNotFoundException;
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getGiftCertificates(boolean sortingByName, boolean sortingByDate, boolean descOrdering);
    List<GiftCertificate> getGiftCertificates();
    List<GiftCertificate> getGiftCertificatesByTagName(String tagName, boolean sortingByName, boolean sortingByDate, boolean descOrdering);
    List<GiftCertificate> getGiftCertificatesByName(String name, boolean sortingByName, boolean sortingByDate, boolean descOrdering);
    List<GiftCertificate> getGiftCertificatesByQuery(String query);

    List<GiftCertificate> getGiftCertificatesByDescription(String description, boolean sortingByName, boolean sortingByDate, boolean descOrdering);
    void deleteGiftCertificate(long id);
}
