package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

public interface GiftCertificateTagDao {
    GiftCertificateTag saveGiftTag(GiftCertificateTag giftCertificateTag);
    GiftCertificateTag getGiftCertificateTag(Long id);
    List<GiftCertificateTag> getGiftCertificateTags();
    void deleteGiftTag(Long id);
    void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId);

}
