package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.GiftCertificateTagService;
import com.epam.esm.services.TagService;
import com.epam.esm.utils.GiftCertificateValidator;
import com.epam.esm.utils.QueryGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.epam.esm.constants.QueryParams.*;
import static com.epam.esm.exceptions.ExceptionCodes.NOT_SUPPORTED;
import static com.epam.esm.exceptions.ExceptionCodes.WRONG_PARAMETER;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    Logger logger = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);
    private final GiftCertificateDao dao;
    private final TagService tagService;
    private final GiftCertificateTagService giftCertificateTagService;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao dao, TagService tagService, GiftCertificateTagService giftCertificateTagService) {
        this.dao = dao;
        this.tagService = tagService;
        this.giftCertificateTagService = giftCertificateTagService;
    }

    @Transactional
    @Override
    public GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) {
        logger.info("Saving new gift certificate with {}}", giftCertificate);
        giftCertificate.setCreateDate(LocalDateTime.now());
        GiftCertificate savedGiftCertificate = dao.saveGiftCertificate(giftCertificate);
        List<Tag> tagList = giftCertificate.getTagList();
        if (tagList != null) {
            updatingTags(giftCertificate.getTagList(), savedGiftCertificate);
        }
        return getGiftCertificatesById(savedGiftCertificate.getId());
    }

    private void updatingTags(List<Tag> tagList, GiftCertificate giftCertificate) {
        long giftCertificateId = giftCertificate.getId();
        List<Tag> tagsFromDb = getGiftCertificatesById(giftCertificateId).getTagList();
        if (tagList.equals(tagsFromDb)) {
            return;
        }
        if (tagsFromDb != null) {
            deleteTags(tagList, tagsFromDb, giftCertificateId);
        }

        List<Tag> savedTag = tagService.getTags();
        List<GiftCertificateTag> giftCertificateTagList = giftCertificateTagService.getGiftCertificateTags();
        for (int i = 0; i < tagList.size(); i++) {
            Tag currTag = tagList.get(i);
            long tagId;
            if (!savedTag.contains(currTag)) {
                tagId = tagService.saveTag(currTag.getName()).getId();
            } else {
                tagId = tagService.getTagByName(currTag.getName()).getId();
            }
            GiftCertificateTag certificateTag = new GiftCertificateTag(giftCertificateId, tagId);
            if (!giftCertificateTagList.contains(certificateTag)) {
                try {
                    giftCertificateTagService.saveGiftCertificateTag(giftCertificateId, tagId);
                }
                catch (RuntimeException e) {
                    logger.info(" this tag was added to certificate");
                }
            }
        }
    }

    private void deleteTags(List<Tag> tagList, List<Tag> tagsFromDb, long giftCertificateId) {
        for (Tag tag : tagsFromDb) {
            if (!tagList.contains(tag)) {
                giftCertificateTagService.deleteGiftCertificateTagByTagAndGiftCertificateId(giftCertificateId, tag.getId());
            }
        }
    }

    @Override
    public GiftCertificate updateGiftCertificate(Long id, GiftCertificate giftCertificate) {
        GiftCertificateValidator validator = new GiftCertificateValidator();
        Map<String, Object> params = validator.getActualParams(giftCertificate);
        logger.info("Updating gift certificate with id = {}, new params = {}", id, params);
        GiftCertificate updatingGiftCertificate = getGiftCertificatesById(id);
        if (!params.isEmpty()) {
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
                        updatingTags((List<Tag>) entry.getValue(), updatingGiftCertificate);
                        break;
                    default:
                        logger.debug("Not supported parameter = {}", entry.getValue());
                        throw new WrongParameterException("Wrong parameter in updating gift certificate", WRONG_PARAMETER);
                }
            }
            updatingGiftCertificate.setLastUpdateDate(LocalDateTime.now());
            dao.updateGiftCertificate(updatingGiftCertificate);
        }
        return getGiftCertificatesById(id);
    }

    @Override
    public GiftCertificate getGiftCertificatesById(Long id) {
        logger.info("Getting gift certificate by id = {}", id);
        return dao.getGiftCertificate(id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        logger.info("Getting all gift certificates");
        return dao.getGiftCertificates();
    }

    @Transactional
    @Override
    public void deleteGiftCertificate(long id) {
        logger.info("Deleting gift certificate with id = {}", id);
        dao.deleteGiftCertificate(id);
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByParameter(Map<String, String> filteredBy, List<String> orderingBy, String order) {
        logger.info("Getting all gift certificates filtered by {} ordering by {}, order = {} ", filteredBy, orderingBy, order);
        return dao.getGiftCertificatesByQuery(getQuery(filteredBy, orderingBy, order));
    }

    String getQuery(Map<String, String> filteredBy, List<String> orderingBy, String order) {
        QueryGenerator queryGenerator = new QueryGenerator();
        if (filteredBy != null) {
            for (Map.Entry<String, String> entry : filteredBy.entrySet()) {
                if (entry.getValue() != null) {
                    switch (entry.getKey()) {
                        case NAME:
                            queryGenerator.addSelectByName(entry.getValue());
                            break;
                        case DESCRIPTION:
                            queryGenerator.addSelectByDescription(entry.getValue());
                            break;
                        case TAG_NAME:
                            queryGenerator.addSelectByTagName(entry.getValue());
                            break;
                        default:
                            logger.debug("Not supported filter = {}", filteredBy);
                            throw new WrongParameterException("Non supported filtering parameter", NOT_SUPPORTED);
                    }
                }
            }
        }
        if (orderingBy != null) {
            for (String s : orderingBy) {
                if (!s.equalsIgnoreCase(DATE) && !s.equalsIgnoreCase(NAME)) {
                    logger.debug("Not supported ordering = {}", s);
                    throw new WrongParameterException("Not supported ordering parameter", NOT_SUPPORTED);
                }
                queryGenerator.addSorting(s);
            }
        }
        if (order != null && order.equalsIgnoreCase("desc")) {
            queryGenerator.addDescOrdering();
        }
        return queryGenerator.getQuery();
    }
}
