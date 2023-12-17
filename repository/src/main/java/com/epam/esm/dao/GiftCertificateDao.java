package com.epam.esm.dao;

import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends CRDDao<GiftCertificate> {
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate)  ;
    List<GiftCertificate> getGiftCertificatesBySortingParams(Map<String, String> filteredBy, List<String> orderingBy) throws  WrongParameterException;
}
