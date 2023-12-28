package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository,
                                      TagService tagService) {
        this.repository = repository;
        this.tagService = tagService;
    }

    /**
     * Save new instants of gift certificate and save tags (if they are new) and creating their pairs
     *
     * @param giftCertificate - new Entity
     * @return {@link GiftCertificate} with generated id
     */
    @Transactional
    @Override
    public GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) throws WrongParameterException {
        log.info("Saving new gift certificate with {}}", giftCertificate);
        giftCertificate.setCreateDate(LocalDateTime.now());
        if (giftCertificate.getTagSet() != null) {
            updateTags(giftCertificate.getTagSet(), giftCertificate);
        }
        try {
            return repository.save(giftCertificate);
        } catch (RuntimeException e) {
            log.warn("Not correct values");
            throw new WrongParameterException("during saving new gift-certificate", WRONG_PARAMETER);
        }
    }

    private void updateTags(Set<Tag> tagList, GiftCertificate giftCertificate) throws WrongParameterException {
        List<String> tagNames = tagList.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        Set<Tag> tags = tagService.findAllByNameIn(tagNames);
        for (String tagName : tagNames) {
            boolean tagExists = tags.stream()
                    .anyMatch(tag -> tag.getName().equalsIgnoreCase(tagName));
            if (!tagExists) {
                tags.add(tagService.saveTag(new Tag(tagName)));
            }
        }
        giftCertificate.setTagSet(tags);
    }

    /**
     * Update existing instants of gift certificate and save new tags, delete or create new tag-certificate pairs
     *
     * @param id              - updating instants
     * @param giftCertificate - new entity (maybe not all parameters)
     * @return {@link GiftCertificate} saved instant
     */
    @Override
    @Transactional
    public GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate)
            throws DataNotFoundException, WrongParameterException {
        GiftCertificate updatingGiftCertificate = getGiftCertificatesById(id);
        log.info("Updating gift certificate with id = {}", id);
        updatingGiftCertificate = getActualGiftCertificate(giftCertificate, updatingGiftCertificate);
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
                new DataNotFoundException(String.format(String.valueOf(id)),
                        NOT_FOUND_GIFT_CERTIFICATE));
    }

    /**
     * Retrieves all gift certificates.
     *
     * @return a list of all gift certificates.
     * @throws DataNotFoundException if no gift certificates are found.
     */
    @Override
    public List<GiftCertificate> getAll(int page, int size) throws DataNotFoundException {
        log.info("Getting all gift certificates");
        List<GiftCertificate> giftCertificateList = repository.findAll(page, size);
        if (!giftCertificateList.isEmpty()) {
            return giftCertificateList;
        }
        throw new DataNotFoundException("gift certificates", NOT_FOUND_GIFT_CERTIFICATE);
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
            throw new WrongParameterException("deleting gift certificate", WRONG_PARAMETER);
        }
    }

    @Override
    public GiftCertificate updateGiftCertificateDuration(Long id, int duration) throws DataNotFoundException, WrongParameterException {
        log.info("Updating duration of gift certificate with id = {} to duration = {} ", id, duration);
        if (duration <= 0) {
            log.warn("Wrong parameter in duration: can not be <= 0");
            throw new WrongParameterException("updating gc with negative duration", WRONG_PARAMETER);
        }
        GiftCertificate giftCertificate = getGiftCertificatesById(id);
        giftCertificate.setDuration(duration);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return repository.save(giftCertificate);
    }

    @Override
    public GiftCertificate updateGiftCertificatePrice(Long id, float price) throws DataNotFoundException, WrongParameterException {
        log.info("Updating duration of gift certificate with id = {} to price = {} ", id, price);
        if (price <= 0) {
            log.warn("Wrong parameter in price: can not be <= 0");
            throw new WrongParameterException("updating gc with negative price", WRONG_PARAMETER);
        }
        GiftCertificate giftCertificate = getGiftCertificatesById(id);
        giftCertificate.setPrice(price);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return repository.save(giftCertificate);
    }

    /**
     * Retrieves gift certificates based on specified parameters.
     *
     * @return a list of gift certificates matching the specified criteria.
     * @throws DataNotFoundException if no gift certificates match the specified criteria.
     */
    @Override
    public List<GiftCertificate> getGiftCertificatesByParameters(int page, int size, String name,
                                                                 String description, Optional<String> tagName)
            throws DataNotFoundException, WrongParameterException {
        log.info("Getting all gift certificates filtered by {}, {} ", name, description);
        try {
            List<GiftCertificate> giftCertificateList = tagName.map(s -> repository
                            .findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTagSet_Name
                                    (name, description, tagName.get(), page, size))
                    .orElseGet(() -> repository
                            .findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase
                                    (name, description, page, size));
            if (!giftCertificateList.isEmpty()) {
                return giftCertificateList;
            }
        } catch (RuntimeException e) {
            log.info("Wrong type in sorting, page = {}, size = {}");
            throw new WrongParameterException("getting certificates: wrong sorting param", WRONG_PARAMETER);
        }
        throw new DataNotFoundException("gift certificates", NOT_FOUND_GIFT_CERTIFICATE);
    }

    @Override
    public List<GiftCertificate> findByTagNames(List<String> tagNames, int page, int size) throws DataNotFoundException {
        log.info("Getting all gift certificates with tags = {} ", tagNames);
        List<GiftCertificate> giftCertificateList = repository.findByTagSet_NameIn(tagNames, size, page);
        if (!giftCertificateList.isEmpty()) {
            return giftCertificateList;
        }
        throw new DataNotFoundException("gift certificates", NOT_FOUND_GIFT_CERTIFICATE);
    }

    private GiftCertificate getActualGiftCertificate(GiftCertificate updatingGiftCertificate, GiftCertificate savedGiftCertificate) throws WrongParameterException {
        log.info("Including new values into gc {}", updatingGiftCertificate);
        if (updatingGiftCertificate.getName() != null) {
            savedGiftCertificate.setName(updatingGiftCertificate.getName());
        }
        if (updatingGiftCertificate.getDescription() != null) {
            savedGiftCertificate.setDescription(updatingGiftCertificate.getDescription());
        }
        if (updatingGiftCertificate.getDuration() != 0) {
            savedGiftCertificate.setDuration(updatingGiftCertificate.getDuration());
        }
        if (updatingGiftCertificate.getPrice() != 0) {
            savedGiftCertificate.setPrice(updatingGiftCertificate.getPrice());
        }
        if (updatingGiftCertificate.getTagSet() != null) {
            updateTags(updatingGiftCertificate.getTagSet(), savedGiftCertificate);
        }
        return savedGiftCertificate;
    }
}
