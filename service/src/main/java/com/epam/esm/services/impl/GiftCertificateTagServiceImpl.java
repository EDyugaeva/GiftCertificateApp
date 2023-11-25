package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.services.GiftCertificateTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodes.WRONG_PARAMETER;

@Service
public class GiftCertificateTagServiceImpl implements GiftCertificateTagService {
    private final GiftCertificateTagDao dao;
    Logger logger = LoggerFactory.getLogger(GiftCertificateTagServiceImpl.class);

    @Autowired
    public GiftCertificateTagServiceImpl(GiftCertificateTagDao dao) {
        this.dao = dao;
    }

    @Override
    public GiftCertificateTag saveGiftCertificateTag(long giftCertificateId, long tagId) {
        logger.info("Saving tag - gift certificate with id {} and {}", tagId, giftCertificateId);
        GiftCertificateTag savingGiftCertificateTag = new GiftCertificateTag();
        savingGiftCertificateTag.setTagId(tagId);
        savingGiftCertificateTag.setGiftCertificateId(giftCertificateId);
        return dao.saveGiftTag(savingGiftCertificateTag);
    }

    @Override
    public GiftCertificateTag getGiftCertificateTag(long id) {
        logger.info("Getting gift certificate - tag pair with id = {}", id);
        return dao.getGiftCertificateTag(id);
    }

    @Override
    public List<GiftCertificateTag> getGiftCertificateTags() {
        logger.info("Getting gift certificate - tag pairs");
        return dao.getGiftCertificateTags();
    }

    @Override
    public void deleteGiftCertificateTag(long id) {
        logger.info("Deleting gift certificate - tag pair with id = {}", id);
        try {
            getGiftCertificateTag(id);
            dao.deleteGiftTag(id);
        } catch (DataNotFoundException e) {
            throw new WrongParameterException(e.getMessage(), WRONG_PARAMETER);
        }
    }

    @Override
    public void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId) {
        logger.info("Deleting gift certificate - tag pair with certificate id = {} and tag id = {}", giftCertificateId, tagId);
        try {
            dao.deleteGiftCertificateTagByTagAndGiftCertificateId(giftCertificateId, tagId);
        } catch (RuntimeException e) {
            throw new WrongParameterException(e.getMessage(), WRONG_PARAMETER);
        }
    }
}
