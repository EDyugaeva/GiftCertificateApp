package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.services.GiftCertificateTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_FOUND_PAIR;
import static com.epam.esm.exceptions.ExceptionCodesConstants.WRONG_PARAMETER;

/**
 * Implementation of the {@link GiftCertificateTagService} interface that provides
 * CRUD operations for managing gift certificate-tags.
 */
@Slf4j
@Service
public class GiftCertificateTagServiceImpl implements GiftCertificateTagService {
    private final GiftCertificateTagDao dao;

    @Autowired
    public GiftCertificateTagServiceImpl(GiftCertificateTagDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public GiftCertificateTag saveGiftCertificateTag(long giftCertificateId, long tagId)
            throws WrongParameterException {
        try {
            log.info("Saving tag - gift certificate with id {} and {}", tagId, giftCertificateId);
            GiftCertificateTag savingGiftCertificateTag = new GiftCertificateTag();
            savingGiftCertificateTag.setTagId(tagId);
            savingGiftCertificateTag.setGiftCertificateId(giftCertificateId);
            return dao.create(savingGiftCertificateTag);
        } catch (DuplicateKeyException e) {
            throw new WrongParameterException("Wrong parameter in request", WRONG_PARAMETER);
        }
    }

    @Override
    public GiftCertificateTag getGiftCertificateTag(long id) throws DataNotFoundException {
        log.info("Getting gift certificate - tag pair with id = {}", id);
        GiftCertificateTag giftCertificateTag = dao.getById(id);
        if (giftCertificateTag != null) {
            return giftCertificateTag;
        }
        log.warn("Gift Certificate - Tag was not found");
        throw new DataNotFoundException(String.format("Requested resource was not found (id = %d)", id), NOT_FOUND_PAIR);
    }

    @Override
    public List<GiftCertificateTag> getGiftCertificateTags() throws DataNotFoundException {
        log.info("Getting gift certificate - tag pairs");
        List<GiftCertificateTag> giftCertificateTagList = dao.getAll();
        if (giftCertificateTagList != null) {
            return giftCertificateTagList;
        }
        log.warn("Gift Certificate - Tags were not found");
        throw new DataNotFoundException("Requested resource was not found (gift Certificate - TagList)", NOT_FOUND_PAIR);
    }

    @Override
    @Transactional
    public void deleteGiftCertificateTag(long id) throws WrongParameterException {
        try {
            log.info("Deleting gift certificate - tag pair with id = {}", id);
            getGiftCertificateTag(id);
            dao.deleteById(id);
        } catch (DataNotFoundException e) {
            log.error("Exception while deleting gift certificate - tag pair with id = {}", id, e);
            throw new WrongParameterException("Exception while deleting gift certificate - tag pair", WRONG_PARAMETER);
        }
    }

    @Override
    @Transactional
    public void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId)
            throws WrongParameterException {
        log.info("Deleting gift certificate - tag pair with certificate id = {} and tag id = {}", giftCertificateId, tagId);
        try {
            dao.deleteGiftCertificateTagByTagAndGiftCertificateId(giftCertificateId, tagId);
        } catch (UncategorizedSQLException e) {
            log.error("Exception while deleting gift certificate - tag pair with certificate id = {} and tag id = {}",
                    giftCertificateId, tagId, e);
            throw new WrongParameterException("Exception while deleting gift certificate - tag pair", WRONG_PARAMETER);
        }
    }
}
