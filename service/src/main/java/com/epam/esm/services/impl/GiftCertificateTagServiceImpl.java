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

import java.util.List;
@Slf4j
@Service
public class GiftCertificateTagServiceImpl implements GiftCertificateTagService {
    private final GiftCertificateTagDao dao;

    @Autowired
    public GiftCertificateTagServiceImpl(GiftCertificateTagDao dao) {
        this.dao = dao;
    }

    @Override
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
    public void deleteGiftCertificateTag(long id) throws OtherDatabaseException {
        log.info("Deleting gift certificate - tag pair with id = {}", id);
        dao.deleteGiftTag(id);
    }

    @Override
    public void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId) throws OtherDatabaseException {
        log.info("Deleting gift certificate - tag pair with certificate id = {} and tag id = {}", giftCertificateId, tagId);
        dao.deleteGiftCertificateTagByTagAndGiftCertificateId(giftCertificateId, tagId);
    }
}
