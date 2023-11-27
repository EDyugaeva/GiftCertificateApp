package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate)
            throws WrongParameterException, OtherDatabaseException;

    GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate)
            throws WrongParameterException, OtherDatabaseException;

    GiftCertificate getGiftCertificatesById(Long id)
            throws DataNotFoundException;

    List<GiftCertificate> getAll() throws DataNotFoundException;

    void deleteGiftCertificate(long id) throws WrongParameterException;

    List<GiftCertificate> getGiftCertificatesByParameter(Map<String, String> filteredBy,
                                                         List<String> orderingBy,
                                                         String order)
            throws DataNotFoundException, WrongParameterException;
}
