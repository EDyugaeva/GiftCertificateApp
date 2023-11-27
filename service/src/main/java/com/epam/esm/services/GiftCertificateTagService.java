package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

public interface GiftCertificateTagService {
    GiftCertificateTag saveGiftCertificateTag(long giftCertificateId, long tagId)
            throws OtherDatabaseException, WrongParameterException;

    GiftCertificateTag getGiftCertificateTag(long id) throws DataNotFoundException;

    List<GiftCertificateTag> getGiftCertificateTags() throws DataNotFoundException;

    void deleteGiftCertificateTag(long id) throws WrongParameterException;

    void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId) throws WrongParameterException;
}
