package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exceptions.ApplicationDatabaseException;
import com.epam.esm.exceptions.DataNotFoundException;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.epam.esm.constants.QueryParams.*;
import static com.epam.esm.exceptions.ExceptionCodesConstants.*;

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
     * @throws ApplicationDatabaseException
     */
    @Transactional
    @Override
    public GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) throws WrongParameterException,
            ApplicationDatabaseException {
        try {
            log.info("Saving new gift certificate with {}}", giftCertificate);
            giftCertificate.setCreateDate(LocalDateTime.now());
            //save gc to get correct id
            GiftCertificate savedGiftCertificate = dao.create(giftCertificate);
            //update table tag, if tags were added to gc
            List<Tag> tagList = giftCertificate.getTagList();
            if (tagList != null) {
                updateTags(giftCertificate.getTagList(), savedGiftCertificate);
            }

            return getGiftCertificatesById(savedGiftCertificate.getId());
        } catch (DuplicateKeyException e) {
            throw new WrongParameterException("Wrong parameter in request", WRONG_PARAMETER);
        } catch (DataNotFoundException e) {
            log.error("Exception while getting saved giftCertificate", e);
            throw new ApplicationDatabaseException("Exception while getting saved giftCertificate", OTHER_EXCEPTION);
        }

    }

    /**
     * Update existing instants of gift certificate and save new tags, delete or create new tag-certificate pairs
     *
     * @param id              - updating instants
     * @param giftCertificate - new entity (maybe not all parameters)
     * @return {@link GiftCertificate} saved instant
     * @throws WrongParameterException
     * @throws ApplicationDatabaseException
     */
    @Override
    public GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate)
            throws WrongParameterException, ApplicationDatabaseException {
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
        GiftCertificate giftCertificate = dao.getById(id);
        if (giftCertificate != null) {
            return giftCertificate;
        }
        throw new DataNotFoundException(String.format("Requested resource was not found (id = %d)", id),
                NOT_FOUND_GIFT_CERTIFICATE);
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
        List<GiftCertificate> giftCertificateList = dao.getAll();
        if (giftCertificateList != null) {
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
            dao.deleteById(id);
        } catch (DataNotFoundException e) {
            throw new WrongParameterException(e.getMessage(), WRONG_PARAMETER);
        }

    }

    /**
     * Retrieves gift certificates based on specified parameters.
     *
     * @param filteredBy a map of filter criteria to apply on the gift certificates.
     * @param orderingBy a list of fields to use for sorting the result (with sorting type).
     * @return a list of gift certificates matching the specified criteria.
     * @throws DataNotFoundException   if no gift certificates match the specified criteria.
     * @throws WrongParameterException if there is an issue with the input parameters.
     */
    @Override
    public List<GiftCertificate> getGiftCertificatesByParameter(Map<String, String> filteredBy,
                                                                List<String> orderingBy) throws DataNotFoundException, WrongParameterException {
        log.info("Getting all gift certificates filtered by {} ordering by {} ", filteredBy, orderingBy);

        List<GiftCertificate> giftCertificateList = dao.getGiftCertificatesBySortingParams(filteredBy, orderingBy);
        if (giftCertificateList != null) {
            return giftCertificateList;
        }
        throw new DataNotFoundException("Requested resource was not found (goft certificates)", NOT_FOUND_GIFT_CERTIFICATE);
    }

    /**
     * Updates the values of the GiftCertificate based on the provided parameters.
     *
     * @param params                  The parameters to update.
     * @param updatingGiftCertificate The GiftCertificate to update.
     */
    private void updateGiftCertificateValues(Map<String, Object> params, GiftCertificate updatingGiftCertificate)
            throws WrongParameterException, DataNotFoundException, ApplicationDatabaseException {
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
            throws WrongParameterException, ApplicationDatabaseException {
        long giftCertificateId = giftCertificate.getId();
        // Step 1: Delete tags that are no longer associated with the gift certificate
        try {
            List<Tag> tagsFromDb = getGiftCertificatesById(giftCertificateId).getTagList();
            if (!tagList.equals(tagsFromDb) && tagsFromDb != null) {
                deleteTags(tagList, tagsFromDb, giftCertificateId);
            }
        } catch (DataNotFoundException e) {
            log.warn("tags fromDb with this certificate are empty");
        }

        // Step 2: Add new tags or associate existing tags with the gift certificate
        try {
            List<Tag> savedTag = tagService.getTags();
            List<GiftCertificateTag> giftCertificateTagList = giftCertificateTagService.getGiftCertificateTags();
            for (Tag currTag : tagList) {

                System.out.println(currTag.getName());

                long tagId = savedTag.contains(currTag) ?
                        tagService.getTagByName(currTag.getName()).getId()
                        : tagService.saveTag(currTag.getName()).getId();

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
