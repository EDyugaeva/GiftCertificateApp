package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.utils.GiftCertificateParamsCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.constants.Constants.GiftCertificateColumn.*;
import static com.epam.esm.constants.Constants.NAME;
import static com.epam.esm.constants.Constants.TAGS;
import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_FOUND_GIFT_CERTIFICATE;
import static com.epam.esm.exceptions.ExceptionCodesConstants.WRONG_PARAMETER;

/**
 * Implementation of the {@link GiftCertificateService} interface.
 * Provides operations related to GiftCertificates, such as creation, update, deletion, and retrieval.
 */
@Service
@Slf4j
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    /**
     * Save new instants of gift certificate and save tags (if they are new) and creating their pairs
     *
     * @param giftCertificate - new Entity
     * @return {@link GiftCertificate} with generated id
     */
    @Transactional
    @Override
    public GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) {
        log.info("Saving new gift certificate with {}}", giftCertificate);
        giftCertificate.setCreateDate(LocalDateTime.now());
        return repository.save(giftCertificate);
    }

    /**
     * Update existing instants of gift certificate and save new tags, delete or create new tag-certificate pairs
     *
     * @param id              - updating instants
     * @param giftCertificate - new entity (maybe not all parameters)
     * @return {@link GiftCertificate} saved instant
     */
    @Override
    public GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate)
            throws DataNotFoundException, WrongParameterException {
        GiftCertificateParamsCreator giftCertificateParamsCreator = new GiftCertificateParamsCreator();
        Map<String, Object> params = giftCertificateParamsCreator.getActualParams(giftCertificate);
        log.info("Updating gift certificate with id = {}, new params = {}", id, params);
        GiftCertificate updatingGiftCertificate = getGiftCertificatesById(id);
        updateGiftCertificateValues(params, updatingGiftCertificate);
        updatingGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        return repository.save(updatingGiftCertificate);
    }


    /**
     * Retrieves a gift certificate by its unique identifier.
     *
     * @param id the unique identifier of the gift certificate to retrieve.
     * @return the {@link GiftCertificate} with the specified ID.
     * @throws DataNotFoundException if the gift certificate with the specified ID is not found.
     */
    @Override
    public GiftCertificate getGiftCertificatesById(Long id) throws DataNotFoundException {
        log.info("Getting gift certificate by id = {}", id);
        Optional<GiftCertificate> giftCertificate = repository.findById(id);
        return giftCertificate.orElseThrow(() ->
                new DataNotFoundException(String.format("Requested resource was not found (id = %d)", id),
                        NOT_FOUND_GIFT_CERTIFICATE));
    }

    /**
     * Retrieves all gift certificates.
     *
     * @return a list of all gift certificates.
     * @throws DataNotFoundException if no gift certificates are found.
     */
    @Override
    public List<GiftCertificate> getAll(Pageable pageable) throws DataNotFoundException {
        log.info("Getting all gift certificates");
        List<GiftCertificate> giftCertificateList = repository.findAll(pageable).getContent();
        if (!giftCertificateList.isEmpty()) {
            return giftCertificateList;
        }
        throw new DataNotFoundException("Requested resource was not found (gift certificates)", NOT_FOUND_GIFT_CERTIFICATE);
    }

    /**
     * Deletes a gift certificate by its unique identifier.
     *
     * @param id the unique identifier of the gift certificate to delete.
     * @throws WrongParameterException if an error occurs while deleting the gift certificate.
     */
    @Transactional
    @Override
    public void deleteGiftCertificate(long id) throws WrongParameterException {
        log.info("Deleting gift certificate with id = {}", id);
        try {
            getGiftCertificatesById(id);
            repository.deleteById(id);
        } catch (DataNotFoundException e) {
            log.info("Deleting gift certificate with id = {} is not possible, there is no such entity", id);
            throw new WrongParameterException(e.getMessage(), WRONG_PARAMETER);
        }
    }

    @Override
    public GiftCertificate updateGiftCertificateDuration(Long id, int duration) throws DataNotFoundException {
        log.info("Updating duration of gift certificate with id = {} to duration = {} ", id, duration);
        GiftCertificate giftCertificate = getGiftCertificatesById(id);
        giftCertificate.setDuration(duration);
        return repository.save(giftCertificate);
    }

    @Override
    public GiftCertificate updateGiftCertificatePrice(Long id, float price) throws DataNotFoundException {
        log.info("Updating duration of gift certificate with id = {} to price = {} ", id, price);
        GiftCertificate giftCertificate = getGiftCertificatesById(id);
        giftCertificate.setPrice(price);
        return repository.save(giftCertificate);
    }

    /**
     * Retrieves gift certificates based on specified parameters.
     *
     * @return a list of gift certificates matching the specified criteria.
     * @throws DataNotFoundException   if no gift certificates match the specified criteria.
     */
    @Override
    public List<GiftCertificate> getGiftCertificatesByParameters(Pageable pageable, String name,
                                                                 String description, Optional<String> tagName)
            throws DataNotFoundException, WrongParameterException {
        log.info("Getting all gift certificates filtered by {}, {}, {} ", name, description, tagName);
        try {
            List<GiftCertificate> giftCertificateList = tagName.map(s -> repository
                            .findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTagList_Name
                                    (name, description, tagName.get(), pageable).getContent())
                    .orElseGet(() -> repository
                            .findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase
                                    (name, description, pageable).getContent());
            if (!giftCertificateList.isEmpty()) {
                return giftCertificateList;
            }
        }
        catch (PropertyReferenceException e) {
            log.info("Wrong type in sorting, pageable = {}", pageable, e);
            throw new WrongParameterException("Wrong sorting param", WRONG_PARAMETER);
        }
        throw new DataNotFoundException("Requested resource was not found (gift certificates)", NOT_FOUND_GIFT_CERTIFICATE);
    }

    /**
     * Updates the values of the GiftCertificate based on the provided parameters.
     *
     * @param params                  The parameters to update.
     * @param updatingGiftCertificate The GiftCertificate to update.
     */
    private void updateGiftCertificateValues(Map<String, Object> params, GiftCertificate updatingGiftCertificate)
            throws WrongParameterException {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case NAME:
                    updatingGiftCertificate.setName((String) entry.getValue());
                    break;
                case DURATION:
                    updatingGiftCertificate.setDuration((Integer) entry.getValue());
                    break;
                case PRICE:
                    updatingGiftCertificate.setPrice((Float) entry.getValue());
                    break;
                case DESCRIPTION:
                    updatingGiftCertificate.setDescription((String) entry.getValue());
                    break;
                case TAGS:
                    updatingGiftCertificate.setTagList((List<Tag>) entry.getValue());
                    break;
                default:
                    log.debug("Not supported parameter = {}", entry.getValue());
                    throw new WrongParameterException("Wrong parameter in updating gift certificate", WRONG_PARAMETER);
            }
        }
    }
}
