package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private Long id;

    private String name;

    private List<GiftCertificate> giftCertificateList;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<GiftCertificate> getGiftCertificateList() {
        return giftCertificateList;
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
