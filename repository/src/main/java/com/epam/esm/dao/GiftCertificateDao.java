package com.epam.esm.dao;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ApplicationDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) throws WrongParameterException, ApplicationDatabaseException;
    GiftCertificate getGiftCertificate(long id) throws DataNotFoundException;
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate) throws DataNotFoundException;
    List<GiftCertificate> getGiftCertificates() throws DataNotFoundException;
    List<GiftCertificate> getGiftCertificatesByQuery(Map<String, String> filteredBy, List<String> orderingBy) throws DataNotFoundException, WrongParameterException;
    void deleteGiftCertificate(long id) throws ApplicationDatabaseException;
}
