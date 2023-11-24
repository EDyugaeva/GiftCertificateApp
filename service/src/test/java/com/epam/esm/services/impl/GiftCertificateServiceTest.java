package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ExceptionCodes;
import com.epam.esm.exceptions.NotSupportedSortingException;
import com.epam.esm.exceptions.WrongParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.epam.esm.constants.GiftCertificatesTestConstants.*;
import static com.epam.esm.constants.QueryParams.*;
import static com.epam.esm.constants.TagTestConstants.TAG_1;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDao mock = Mockito.mock(GiftCertificateDao.class);

    @InjectMocks
    private GiftCertificateServiceImpl service;

    @Test
    public void getGiftCertificate_expectedGiftCertificate_whenGetting1GiftCertificate() {
        when(mock.getGiftCertificate(GIFT_CERTIFICATE_1.getId())).thenReturn(GIFT_CERTIFICATE_1);
        assertEquals("Actual gift certificate should be equal to expected",
                GIFT_CERTIFICATE_1, service.getGiftCertificatesById(GIFT_CERTIFICATE_1.getId()));
    }

    @Test
    public void getAll_expectedGiftCertificateListWithoutOrdering_whenGetting1GiftCertificates() {
        when(mock.getGiftCertificates()).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getAll());
    }

    @Test
    public void getQuery_emptyString_whenNullParams() {
        assertEquals("Actual query should be empty", service.getQuery(null, null, ""), "");
    }

    @Test
    public void getQuery_exception_whenWrongParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put(NAME_VALUE, NAME_VALUE);
        NotSupportedSortingException exception = assertThrows(NotSupportedSortingException.class,
                () -> service.getQuery(map, null, null),
                "Tag should be not found and  DataNotFoundException should be thrown");
        assertEquals("Exception code should be " + ExceptionCodes.NOT_SUPPORTED.getErrorCode(),
                ExceptionCodes.NOT_SUPPORTED.getErrorCode(), exception.getMessage());
    }

    @Test
    public void getQuery_queryWithAllParams_whenSortingByAllParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put(NAME, NAME_VALUE);
        map.put(DESCRIPTION, DESCRIPTION_VALUE);
        map.put(TAG_NAME, TAG_NAME_VALUE);

        List<String> ordering = Arrays.asList(NAME, DATE);
        String query = service.getQuery(map, ordering, DESC);
        System.out.println(query);
        assertTrue("Actual query should include desc", StringUtils.containsIgnoreCase(query, DESC));
        assertFalse("Actual query should not include asc", StringUtils.containsIgnoreCase(query, ASC));

        assertTrue("Actual query should include name", StringUtils.containsIgnoreCase(query, NAME));
        assertTrue("Actual query should include name param", StringUtils.containsIgnoreCase(query, NAME_VALUE));

        assertTrue("Actual query should include description", StringUtils.containsIgnoreCase(query, DESCRIPTION));
        assertTrue("Actual query should include description param", StringUtils.containsIgnoreCase(query, DESCRIPTION_VALUE));

        assertTrue("Actual query should include tagName", StringUtils.containsIgnoreCase(query, TAG_NAME_IN_QUERY));
        assertTrue("Actual query should include tag name value", StringUtils.containsIgnoreCase(query, TAG_NAME_VALUE));

        assertTrue("Actual query should include ordering", StringUtils.containsIgnoreCase(query, "ORDER BY"));
        assertTrue("Actual query should include order parameter", StringUtils.containsIgnoreCase(query, DATA_IN_QUERY ));
    }


}
