package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.GiftCertificateTagService;
import com.epam.esm.services.TagService;
import com.epam.esm.utils.GiftCertificateParamsCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.epam.esm.constants.QueryParams.*;
import static com.epam.esm.exceptions.ExceptionCodes.EMPTY_REQUEST;
import static com.epam.esm.exceptions.ExceptionCodes.WRONG_PARAMETER;
import static com.epam.esm.exceptions.ExceptionCodesConstants.OTHER_EXCEPTION;

/**
 * Implementation of the {@link GiftCertificateService} interface.
 * Provides operations related to GiftCertificates, such as creation, update, deletion, and retrieval.
 */
@Service
@Slf4j
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao dao;
    private final TagService tagService;
    private final GiftCertificateTagService giftCertificateTagService;

    /**
     * Constructs a new instance of the GiftCertificateServiceImpl.
     *
     * @param dao                       The data access object for GiftCertificates.
     * @param tagService                The service for Tag-related operations.
     * @param giftCertificateTagService The service for GiftCertificateTag-related operations.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao dao,
                                      TagService tagService,
                                      GiftCertificateTagService giftCertificateTagService) {
        this.dao = dao;
        this.tagService = tagService;
        this.giftCertificateTagService = giftCertificateTagService;
    }

    /**
     * Save new instants of gift certificate and save tags (if they are new) and creating their pairs
     *
     * @param giftCertificate - new Entity
     * @return {@link GiftCertificate} with generated id
     * @throws WrongParameterException
     * @throws OtherDatabaseException
     */
    @Transactional
    @Override
    public GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) throws WrongParameterException,
            OtherDatabaseException {
        log.info("Saving new gift certificate with {}}", giftCertificate);
        giftCertificate.setCreateDate(LocalDateTime.now());
        GiftCertificate savedGiftCertificate = dao.saveGiftCertificate(giftCertificate);
        List<Tag> tagList = giftCertificate.getTagList();
        if (tagList != null) {
            updateTags(giftCertificate.getTagList(), savedGiftCertificate);
        }
        try {
            return getGiftCertificatesById(savedGiftCertificate.getId());
        } catch (DataNotFoundException e) {
            throw new OtherDatabaseException("Exception while saving gift certificate ", OTHER_EXCEPTION);
        }
    }

    /**
     * Update existing instants of gift certificate and save new tags, delete or create new tag-certificate pairs
     *
     * @param id              - updating instants
     * @param giftCertificate - new entity (maybe not all parameters)
     * @return {@link GiftCertificate} saved instant
     * @throws WrongParameterException
     * @throws OtherDatabaseException
     */
    @Override
    public GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate)
            throws WrongParameterException, OtherDatabaseException {
        GiftCertificateParamsCreator giftCertificateParamsCreator = new GiftCertificateParamsCreator();
        Map<String, Object> params = giftCertificateParamsCreator.getActualParams(giftCertificate);
        log.info("Updating gift certificate with id = {}, new params = {}", id, params);
        try {
            GiftCertificate updatingGiftCertificate = getGiftCertificatesById(id);
            if (!params.isEmpty()) {
                updateGiftCertificateValues(params, updatingGiftCertificate);
                updatingGiftCertificate.setLastUpdateDate(LocalDateTime.now());
                dao.updateGiftCertificate(updatingGiftCertificate);
            } else {
                throw new WrongParameterException("Request value is not correct", EMPTY_REQUEST);
            }
            return getGiftCertificatesById(id);
        } catch (DataNotFoundException e) {
            log.error("There is no gift certificate with id = {}", id);
            throw new WrongParameterException(String.format("There is no gift certificate with id %d", id), WRONG_PARAMETER);
        }
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
        return dao.getGiftCertificate(id);
    }

    /**
     * Retrieves all gift certificates.
     *
     * @return a list of all gift certificates.
     * @throws DataNotFoundException if no gift certificates are found.
     */
    @Override
    public List<GiftCertificate> getAll() throws DataNotFoundException {
        log.info("Getting all gift certificates");
        return dao.getGiftCertificates();
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
            dao.deleteGiftCertificate(id);
        } catch (Exception e) {
            throw new WrongParameterException(e.getMessage(), WRONG_PARAMETER);
        }

    }

    /**
     * Retrieves gift certificates based on specified parameters.
     *
     * @param filteredBy a map of filter criteria to apply on the gift certificates.
     * @param orderingBy a list of fields to use for sorting the result.
     * @param order      the order in which the result should be sorted (ASC or DESC).
     * @return a list of gift certificates matching the specified criteria.
     * @throws DataNotFoundException   if no gift certificates match the specified criteria.
     * @throws WrongParameterException if there is an issue with the input parameters.
     */
    @Override
    public List<GiftCertificate> getGiftCertificatesByParameter(Map<String, String> filteredBy,
                                                                List<String> orderingBy,
                                                                String order) throws DataNotFoundException, WrongParameterException {
        log.info("Getting all gift certificates filtered by {} ordering by {}, order = {} ", filteredBy, orderingBy, order);
        return dao.getGiftCertificatesByQuery(filteredBy, orderingBy, order);
    }

    /**
     * Updates the values of the GiftCertificate based on the provided parameters.
     *
     * @param params                  The parameters to update.
     * @param updatingGiftCertificate The GiftCertificate to update.
     */
    private void updateGiftCertificateValues(Map<String, Object> params, GiftCertificate updatingGiftCertificate)
            throws WrongParameterException, DataNotFoundException, OtherDatabaseException {
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
                    updateTags((List<Tag>) entry.getValue(), updatingGiftCertificate);
                    break;
                default:
                    log.debug("Not supported parameter = {}", entry.getValue());
                    throw new WrongParameterException("Wrong parameter in updating gift certificate", WRONG_PARAMETER);
            }
        }
    }

    private void updateTags(List<Tag> tagList, GiftCertificate giftCertificate)
            throws WrongParameterException, OtherDatabaseException {
        long giftCertificateId = giftCertificate.getId();
        // Step 1: Delete tags that are no longer associated with the gift certificate
        try {
            List<Tag> tagsFromDb = getGiftCertificatesById(giftCertificateId).getTagList();
            if (!tagList.equals(tagsFromDb)) {
                deleteTags(tagList, tagsFromDb, giftCertificateId);
            }
        } catch (DataNotFoundException e) {
            log.warn("tags fromDb with this certificate are empty");
        }

        // Step 2: Add new tags or associate existing tags with the gift certificate
        try {
            List<Tag> savedTag = tagService.getTags();
            List<GiftCertificateTag> giftCertificateTagList = giftCertificateTagService.getGiftCertificateTags();
            for (int i = 0; i < tagList.size(); i++) {
                Tag currTag = tagList.get(i);
                long tagId = savedTag.contains(currTag) ?
                        tagService.getTagByName(currTag.getName()).getId() : tagService.saveTag(currTag.getName()).getId();

                GiftCertificateTag certificateTag = new GiftCertificateTag(giftCertificateId, tagId);
                if (!giftCertificateTagList.contains(certificateTag)) {
                    giftCertificateTagService.saveGiftCertificateTag(giftCertificateId, tagId);
                }
            }
        } catch (DataNotFoundException e) {
            log.warn("Tags from the db or tag-gift pairs are empty");
        }
    }

    private void deleteTags(List<Tag> tagList, List<Tag> tagsFromDb, long giftCertificateId) throws WrongParameterException {
        for (Tag tag : tagsFromDb) {
            if (!tagList.contains(tag)) {
                giftCertificateTagService.deleteGiftCertificateTagByTagAndGiftCertificateId(giftCertificateId, tag.getId());
            }
        }
    }
}
