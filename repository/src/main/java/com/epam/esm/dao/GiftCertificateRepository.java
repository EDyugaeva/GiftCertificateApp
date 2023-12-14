package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
//    List<GiftCertificate> getGiftCertificatesBySortingParams(Map<String, String> filteredBy, List<String> orderingBy) throws  WrongParameterException;

}
