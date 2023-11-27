package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.services.GiftCertificateTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodes.WRONG_PARAMETER;

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
            throws OtherDatabaseException, WrongParameterException {
        log.info("Saving tag - gift certificate with id {} and {}", tagId, giftCertificateId);
        GiftCertificateTag savingGiftCertificateTag = new GiftCertificateTag();
        savingGiftCertificateTag.setTagId(tagId);
        savingGiftCertificateTag.setGiftCertificateId(giftCertificateId);
        return dao.saveGiftTag(savingGiftCertificateTag);
    }

    @Override
    public GiftCertificateTag getGiftCertificateTag(long id) throws DataNotFoundException {
        log.info("Getting gift certificate - tag pair with id = {}", id);
        return dao.getGiftCertificateTag(id);
    }

    @Override
    public List<GiftCertificateTag> getGiftCertificateTags() throws DataNotFoundException {
        log.info("Getting gift certificate - tag pairs");
        return dao.getGiftCertificateTags();
    }

    @Override
    @Transactional
    public void deleteGiftCertificateTag(long id) throws WrongParameterException {
        log.info("Deleting gift certificate - tag pair with id = {}", id);
        try {
            dao.getGiftCertificateTag(id);
            dao.deleteGiftTag(id);
        } catch (Exception e) {
            log.error("Exception while deleting gift certificate - tag pair with id = {}", id, e);
            throw new WrongParameterException("Exception while deleting gift certificate - tag pair", WRONG_PARAMETER);
        }
    }

    @Override
    @Transactional
    public void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId) throws
            WrongParameterException {
        log.info("Deleting gift certificate - tag pair with certificate id = {} and tag id = {}", giftCertificateId, tagId);
        try {
            dao.deleteGiftCertificateTagByTagAndGiftCertificateId(giftCertificateId, tagId);
        }
        catch (Exception e) {
            log.error("Exception while deleting gift certificate - tag pair with certificate id = {} and tag id = {}",
                    giftCertificateId, tagId, e);
            throw new WrongParameterException("Exception while deleting gift certificate - tag pair", WRONG_PARAMETER);
        }
    }
}
