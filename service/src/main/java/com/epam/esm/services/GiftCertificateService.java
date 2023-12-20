package com.epam.esm.services;

import com.epam.esm.exceptions.ApplicationException;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate)
            throws WrongParameterException, ApplicationException;

    GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate)
            throws WrongParameterException, DataNotFoundException;

    GiftCertificate getGiftCertificatesById(Long id) throws DataNotFoundException;

    List<GiftCertificate> getAll(Pageable pageable) throws DataNotFoundException;

    void deleteGiftCertificate(long id) throws WrongParameterException;

    GiftCertificate updateGiftCertificateDuration(Long id, int duration) throws DataNotFoundException, WrongParameterException;

    GiftCertificate updateGiftCertificatePrice(Long id, float price) throws DataNotFoundException, WrongParameterException;

    List<GiftCertificate> getGiftCertificatesByParameters(Pageable pageable, String name,
                                                          String description, Optional<String> tagName)
            throws DataNotFoundException, WrongParameterException;

    List<GiftCertificate> findByTagNames(List<String> tagNames, Pageable pageable) throws DataNotFoundException;
}
