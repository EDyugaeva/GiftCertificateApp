package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exceptions.ExceptionCodes;
import com.epam.esm.exceptions.NotSupportedSortingException;
import com.epam.esm.exceptions.TestException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.services.GiftCertificateTagService;
import com.epam.esm.services.TagService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.epam.esm.constants.GiftCertificateTagTestConstants.*;
import static com.epam.esm.constants.GiftCertificatesTestConstants.*;
import static com.epam.esm.constants.QueryParams.*;
import static com.epam.esm.constants.TagTestConstants.TAG_1;
import static com.epam.esm.constants.TagTestConstants.TAG_2;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDao mock = Mockito.mock(GiftCertificateDao.class);

    @Mock
    private TagService mockTagService = Mockito.mock(TagService.class);

    @Mock
    private GiftCertificateTagService mockGiftCertificateTagService = Mockito.mock(GiftCertificateTagService.class);

    @InjectMocks
    private GiftCertificateServiceImpl service;

    @Test
    public void getGiftCertificate_expectedGiftCertificate_whenGetting1GiftCertificate() {
        when(mock.getGiftCertificate(GIFT_CERTIFICATE_1.getId())).thenReturn(GIFT_CERTIFICATE_1);
        assertEquals("Actual gift certificate should be equal to expected",
                GIFT_CERTIFICATE_1, service.getGiftCertificatesById(GIFT_CERTIFICATE_1.getId()));
    }

    @Test
    public void getAll_expectedGiftCertificateList_whenGetting1GiftCertificates() throws TestException {
        when(mock.getGiftCertificates()).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getAll());
    }

    @Test
    public void getByParameter_expectedGiftCertificateList_whenGettingGiftCertificatesWithNullParams() throws TestException {
        String query = service.getQuery(null, null, null);
        when(mock.getGiftCertificatesByQuery(query)).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getGiftCertificatesByParameter(null, null, null));
    }

    @Test
    public void getByParameter_expectedGiftCertificateList_whenGettingGiftCertificatesWithParams() throws TestException {
        HashMap<String, String> params = new HashMap<>();
        params.put(NAME, NAME_VALUE);
        String query = service.getQuery(params, Arrays.asList(NAME, DATE), DESC);
        when(mock.getGiftCertificatesByQuery(query)).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getGiftCertificatesByParameter(params, Arrays.asList(NAME, DATE), DESC));
    }

    @Test
    public void getQuery_emptyString_whenNullParams() {
        assertEquals("Actual query should be empty", "", service.getQuery(null, null, ""));
    }

    @Test
    public void getQuery_exception_whenWrongParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put(NAME_VALUE, NAME_VALUE);
        NotSupportedSortingException exception = assertThrows(NotSupportedSortingException.class,
                () -> service.getQuery(map, null, null),
                "Gift certificate should be not found and  DataNotFoundException should be thrown");
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
        assertTrue("Actual query should include desc", StringUtils.containsIgnoreCase(query, DESC));
        assertFalse("Actual query should not include asc", StringUtils.containsIgnoreCase(query, ASC));

        assertTrue("Actual query should include name", StringUtils.containsIgnoreCase(query, NAME));
        assertTrue("Actual query should include name param", StringUtils.containsIgnoreCase(query, NAME_VALUE));

        assertTrue("Actual query should include description", StringUtils.containsIgnoreCase(query, DESCRIPTION));
        assertTrue("Actual query should include description param", StringUtils.containsIgnoreCase(query, DESCRIPTION_VALUE));

        assertTrue("Actual query should include tagName", StringUtils.containsIgnoreCase(query, TAG_NAME_IN_QUERY));
        assertTrue("Actual query should include tag name value", StringUtils.containsIgnoreCase(query, TAG_NAME_VALUE));

        assertTrue("Actual query should include ordering", StringUtils.containsIgnoreCase(query, "ORDER BY"));
        assertTrue("Actual query should include order parameter", StringUtils.containsIgnoreCase(query, DATA_IN_QUERY));
    }

    @Test
    public void saveGiftCertificate_GiftCertificateWithRightParams_whenSavingCorrectGiftCertificate() {
        when(mock.saveGiftCertificate(any())).thenReturn(GIFT_CERTIFICATE_1);
        when(mockTagService.getTags()).thenReturn(TAG_LIST);
        when(mockTagService.getTagByName(TAG_1.getName())).thenReturn(TAG_1);
        when(mockTagService.getTagByName(TAG_2.getName())).thenReturn(TAG_2);
        when(mockGiftCertificateTagService.getGiftCertificateTags()).thenReturn(new ArrayList<>());
        when(mockGiftCertificateTagService.saveGiftCertificateTag(GIFT_CERTIFICATE_1.getId(), TAG_1.getId())).thenReturn(GIFT_TAG_1);
        when(mockGiftCertificateTagService.saveGiftCertificateTag(GIFT_CERTIFICATE_1.getId(), TAG_2.getId())).thenReturn(GIFT_TAG_2);

        GiftCertificate actualGC = service.saveGiftCertificate(GIFT_CERTIFICATE_1);

        assertEquals("Actual name should be equal to expected", GIFT_CERTIFICATE_1.getName(), actualGC.getName());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_1.getDescription(), actualGC.getDescription());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_1.getDuration(), actualGC.getDuration());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_1.getTagList(), actualGC.getTagList());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_1.getPrice(), actualGC.getPrice());
        assertNotNull("Created time should be not empty", actualGC.getCreateDate());
        assertNull("Last update time should be empty", actualGC.getLastUpdateDate());
    }

    @Test
    public void updateGiftCertificate_GiftCertificateWithRightParams_whenUpdatingCorrectGiftCertificate() {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME, GIFT_CERTIFICATE_2.getName());
        params.put(DURATION, GIFT_CERTIFICATE_2.getDuration());
        params.put(PRICE, GIFT_CERTIFICATE_2.getPrice());
        params.put(DESCRIPTION, GIFT_CERTIFICATE_2.getDescription());
        params.put(TAGS, GIFT_CERTIFICATE_2.getTagList());

        when(mock.getGiftCertificate(GIFT_CERTIFICATE_2.getId())).thenReturn(GIFT_CERTIFICATE_2_BEFORE_UPDATE);
        when(mock.updateGiftCertificate(any())).thenReturn(GIFT_CERTIFICATE_2);
        when(mockTagService.getTags()).thenReturn(TAG_LIST);
        when(mockTagService.getTagByName(TAG_1.getName())).thenReturn(TAG_1);
        when(mockTagService.getTagByName(TAG_2.getName())).thenReturn(TAG_2);
        when(mockGiftCertificateTagService.getGiftCertificateTags()).thenReturn(TAG_GIFT_LIST);

        GiftCertificate actualGC = service.updateGiftCertificate(GIFT_CERTIFICATE_2.getId(), params);

        assertEquals("Actual name should be equal to expected", GIFT_CERTIFICATE_2.getName(), actualGC.getName());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_2.getDescription(), actualGC.getDescription());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_2.getDuration(), actualGC.getDuration());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_2.getTagList(), actualGC.getTagList());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_2.getPrice(), actualGC.getPrice());
        assertNotNull("Created time should be not empty", actualGC.getCreateDate());
        assertNotNull("Last update time should be not empty", actualGC.getLastUpdateDate());
    }


}
