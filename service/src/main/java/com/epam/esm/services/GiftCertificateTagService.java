package com.epam.esm.services;

import com.epam.esm.model.GiftCertificateTag;

import java.util.List;

public interface GiftCertificateTagService {
    GiftCertificateTag saveGiftCertificateTag(long giftCertificateId, long tagId);

    GiftCertificateTag getGiftCertificateTag(long id);

    List<GiftCertificateTag> getGiftCertificateTags();

    void deleteGiftCertificateTag(long id);
}
