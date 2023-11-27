package com.epam.esm.dao;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

public interface GiftCertificateTagDao {
    GiftCertificateTag saveGiftTag(GiftCertificateTag giftCertificateTag) throws OtherDatabaseException, WrongParameterException;
    GiftCertificateTag getGiftCertificateTag(Long id) throws DataNotFoundException;
    List<GiftCertificateTag> getGiftCertificateTags() throws DataNotFoundException;
    void deleteGiftTag(Long id) throws OtherDatabaseException;
    void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId) throws OtherDatabaseException;
}
