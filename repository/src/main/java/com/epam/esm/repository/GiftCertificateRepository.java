package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {

    List findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTagSet_Name(String name, String description, String tagName, Pageable pageable);
    List findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);
    List findByTagSet_NameIn(List<String> names, Pageable pageable);
}
