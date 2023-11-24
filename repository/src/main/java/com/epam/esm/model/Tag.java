package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
}
