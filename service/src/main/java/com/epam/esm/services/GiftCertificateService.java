package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ApplicationDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate)
            throws WrongParameterException, ApplicationDatabaseException;

    GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate)
            throws WrongParameterException, ApplicationDatabaseException;

    GiftCertificate getGiftCertificatesById(Long id)
            throws DataNotFoundException;

    List<GiftCertificate> getAll() throws DataNotFoundException;

    void deleteGiftCertificate(long id) throws WrongParameterException;

    List<GiftCertificate> getGiftCertificatesByParameter(Map<String, String> filteredBy,
                                                         List<String> orderingBy)
            throws DataNotFoundException, WrongParameterException;
}
