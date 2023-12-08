package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificateTag;

public interface GiftCertificateTagDao extends CRDDao<GiftCertificateTag> {
    void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId);
}
