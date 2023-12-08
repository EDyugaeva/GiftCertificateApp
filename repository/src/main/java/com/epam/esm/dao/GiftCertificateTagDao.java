package com.epam.esm.dao;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ApplicationDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

//TODO выделить общий интерфейс для общих методов

public interface GiftCertificateTagDao {
    GiftCertificateTag saveGiftTag(GiftCertificateTag giftCertificateTag) throws ApplicationDatabaseException, WrongParameterException;
    GiftCertificateTag getGiftCertificateTag(Long id) throws DataNotFoundException;
    List<GiftCertificateTag> getGiftCertificateTags() throws DataNotFoundException;
    void deleteGiftTag(Long id) throws ApplicationDatabaseException;
    void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId) throws ApplicationDatabaseException;
}
