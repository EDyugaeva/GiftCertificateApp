package com.epam.esm.dao.impl;

import com.epam.esm.config.AppTestConfig;
import com.epam.esm.constants.GiftCertificatesTestConstants;
import com.epam.esm.dao.Constants;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AssertionErrors;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.esm.constants.GiftCertificatesTestConstants.*;
import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_SUPPORTED;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiftCertificateDaoImplTest extends BaseTest{

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;

    @Test
    @Order(1)
    public void getTags_correctTagList_whenGetGiftCertificates()  {
        assertEquals(GIFT_CERTIFICATE_LIST, giftCertificateDao.getAll(),
                "When getting certificates, list should be equal to database values");
    }

    @Test
    public void getGiftCertificate_correctGiftCertificate_whenGetGiftCertificate() {
        assertEquals(GIFT_CERTIFICATE_2, giftCertificateDao.getById(GIFT_CERTIFICATE_2.getId()),
                "When getting certificate, it should be equal to database value");
    }

    @Test
    public void getGiftCertificate_null_whenTryToGetAbsentCertificate() {
       assertNull( giftCertificateDao.getById(ABSENT_ID),
                "Tag should be not found and null should be return");
    }

    @Test()
    public void updateGiftCertificate_updatedGiftCertificate_whenGiftCertificateWasUpdated()  {
        giftCertificateDao.updateGiftCertificate(UPDATED_CERTIFICATE);
        assertEquals(UPDATED_CERTIFICATE, giftCertificateDao.getById(UPDATED_CERTIFICATE.getId()),
                "Tag should be updated");
    }

    @Test
    public void saveGiftCertificate_savedGiftCertificate_whenGiftCertificateWasSaved()   {
        GiftCertificate savingCertificate = NEW_GIFT_CERTIFICATE;
        giftCertificateDao.create(savingCertificate);
        savingCertificate.setId(NEW_ID);
        assertEquals(savingCertificate, giftCertificateDao.getById(NEW_ID), "Gift certificate should be saved with correct ID");
    }

    @Test
    public void deleteGiftCertificate_null_whenTryToGetDeletedGiftCertificate()  {
        giftCertificateDao.deleteById(GIFT_CERTIFICATE_5.getId());
        assertNull(giftCertificateDao.getById(GIFT_CERTIFICATE_5.getId()),
                "Tag should be not found and null should be return");
    }

    @Test
    public void getGiftCertificatesByDescription_certificateList_whenGetListFilterByAllParams() throws  WrongParameterException {
        List<GiftCertificate> giftCertificateList = GIFT_CERTIFICATE_LIST_WITH_TAG_NAME
                .stream()
                .filter(g -> g.getName().contains(NAME_VALUE))
                .filter(g -> g.getDescription().contains(DESCRIPTION_VALUE))
                .sorted(Comparator.comparing(GiftCertificate::getName).thenComparing(GiftCertificate::getCreateDate).reversed())
                .collect(Collectors.toList());
        Map<String, String> map = new HashMap<>();
        map.put(Constants.NAME, NAME_VALUE);
        map.put(DESCRIPTION_VALUE, DESCRIPTION_VALUE);
        map.put(Constants.GiftCertificateColumn.TAG_NAME_QUERY, TAG_NAME_VALUE);

        assertEquals(giftCertificateList, giftCertificateDao.getGiftCertificatesBySortingParams(map, SORTING_VALUE),
                "Actual list should be filtered by all params");
    }

    @Test
    public void getQuery_emptyString_whenNullParams() throws WrongParameterException {
        AssertionErrors.assertEquals("Actual query should be empty", "",
                giftCertificateDao.getQuery(null, null));
    }

    @Test
    public void getQuery_exception_whenWrongParams() {
        Map<String, String> map = new HashMap<>();
        map.put(NAME_VALUE, NAME_VALUE);
        WrongParameterException exception = assertThrows(WrongParameterException.class,
                () -> giftCertificateDao.getQuery(map, null),
                "Gift certificate should be not found and  WrongParameterException should be thrown");
        AssertionErrors.assertEquals("Exception code should be " + NOT_SUPPORTED,
                NOT_SUPPORTED, exception.getErrorCode());
    }

    @Test
    public void getQuery_queryWithAllParams_whenSortingByAllParams() throws WrongParameterException {
        HashMap<String, String> map = new HashMap<>();
        map.put(Constants.NAME, NAME_VALUE);
        map.put(Constants.GiftCertificateColumn.DESCRIPTION, DESCRIPTION_VALUE);
        map.put(Constants.GiftCertificateColumn.TAG_NAME_QUERY, TAG_NAME_VALUE);

        String query = giftCertificateDao.getQuery(map, SORTING_VALUE);

        assertTrue("Actual query should include desc", StringUtils.containsIgnoreCase(query, GiftCertificatesTestConstants.DESC));
        assertFalse("Actual query should not include asc", StringUtils.containsIgnoreCase(query, GiftCertificatesTestConstants.ASC));

        assertTrue("Actual query should include name", StringUtils.containsIgnoreCase(query, Constants.NAME));
        assertTrue("Actual query should include name param", StringUtils.containsIgnoreCase(query, NAME_VALUE));

        assertTrue("Actual query should include description", StringUtils.containsIgnoreCase(query, Constants.GiftCertificateColumn.DESCRIPTION));
        assertTrue("Actual query should include description param", StringUtils.containsIgnoreCase(query, DESCRIPTION_VALUE));

        assertTrue("Actual query should include tagName", StringUtils.containsIgnoreCase(query, TAG_NAME_IN_QUERY));
        assertTrue("Actual query should include tag name value", StringUtils.containsIgnoreCase(query, TAG_NAME_VALUE));

        assertTrue("Actual query should include ordering", StringUtils.containsIgnoreCase(query, ORDER_BY));
        assertTrue("Actual query should include order parameter", StringUtils.containsIgnoreCase(query, DATA_IN_QUERY));
    }
}

