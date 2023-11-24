package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exceptions.NotSupportedSortingException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.epam.esm.constants.QueryParams.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    Logger logger = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificate saveGiftCertificate(String name, String description, float price, int duration) {
        logger.info("Saving new gift certificate with name = {}, description = {}, price = {}, duration = {}", name, description, price, duration);
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setDuration(duration);
        giftCertificate.setCreateDate(LocalDateTime.now());
        return giftCertificateDao.saveGiftCertificate(giftCertificate);
    }

    @Override
    public GiftCertificate updateGiftCertificate(Long id, Map<String, Object> params) {
        logger.info("Updating gift certificate with id = {}, new params = {}", id, params);
        GiftCertificate updatingGiftCertificate = getGiftCertificatesById(id);
        if (!params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                switch (entry.getKey()) {
                    case NAME:
                        updatingGiftCertificate.setName((String) entry.getValue());
                        break;
                    case DURATION:
                        updatingGiftCertificate.setDuration((Integer) entry.getValue());
                        break;
                    case PRICE:
                        updatingGiftCertificate.setPrice((Float) entry.getValue());
                        break;
                    case DESCRIPTION:
                        updatingGiftCertificate.setDescription((String) entry.getValue());
                        break;
                    default:
                        logger.debug("Not supported parameter = {}", entry.getValue());
                        throw new WrongParameterException();
                }
            }
            updatingGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        }
        return giftCertificateDao.updateGiftCertificate(updatingGiftCertificate);
    }

    @Override
    public GiftCertificate getGiftCertificatesById(Long id) {
        logger.info("Getting gift certificate by id = {}", id);
        return giftCertificateDao.getGiftCertificate(id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        logger.info("Getting all gift certificates");
        return giftCertificateDao.getGiftCertificates();
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByParameter(String filteredBy, String filterValue, String orderingBy, boolean desc) {
        logger.info("Getting all gift certificates filtered by {} with value = {} ordering by {}, desc = {} ", filteredBy, filterValue, orderingBy, desc);
        boolean orderingByName = orderingBy.contains(NAME);
        boolean orderingByDate = orderingBy.contains(DATE);
        if (filteredBy.isEmpty()) {
            return giftCertificateDao.getGiftCertificates(orderingByName, orderingByDate, desc);
        } else if (filteredBy.equals(NAME)) {
            return giftCertificateDao.getGiftCertificatesByName(filterValue, orderingByName, orderingByDate, desc);
        } else if (filteredBy.equals(TAG_NAME)) {
            return giftCertificateDao.getGiftCertificatesByTagName(filterValue, orderingByName, orderingByDate, desc);
        } else if (filteredBy.equals(DESCRIPTION)) {
            return giftCertificateDao.getGiftCertificatesByDescription(filterValue, orderingByName, orderingByDate, desc);
        } else {
            logger.debug("Not supported filter = {}", filteredBy);
            throw new NotSupportedSortingException();
        }
    }
}
