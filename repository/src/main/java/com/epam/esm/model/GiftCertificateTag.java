package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateTag {
    private Long id;
    private Long tagId;
    private Long giftCertificateId;

    public GiftCertificateTag(Long tagId, Long giftCertificateId) {
        this.tagId = tagId;
        this.giftCertificateId = giftCertificateId;
    }
}
