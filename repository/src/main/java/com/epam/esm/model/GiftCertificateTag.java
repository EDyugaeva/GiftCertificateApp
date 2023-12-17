package com.epam.esm.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateTag that = (GiftCertificateTag) o;
        return Objects.equals(tagId, that.tagId) && Objects.equals(giftCertificateId, that.giftCertificateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, giftCertificateId);
    }
}
