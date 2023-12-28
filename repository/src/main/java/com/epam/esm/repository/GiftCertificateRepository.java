package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;

import java.util.List;


public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {

    List findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTagSet_Name(String name, String description, String tagName, int page, int size);
    List findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String name, String description, int page, int size);
    List findByTagSet_NameIn(List<String> names, int page, int size);
}
