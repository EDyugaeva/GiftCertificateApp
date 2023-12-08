package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ApplicationDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.services.GiftCertificateTagService;
import com.epam.esm.services.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.epam.esm.constants.GiftCertificateTagTestConstants.*;
import static com.epam.esm.constants.GiftCertificatesTestConstants.*;
import static com.epam.esm.constants.QueryParams.DATE;
import static com.epam.esm.constants.QueryParams.NAME;
import static com.epam.esm.constants.TagTestConstants.TAG_1;
import static com.epam.esm.constants.TagTestConstants.TAG_2;
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
    public void getGiftCertificate_expectedGiftCertificate_whenGetting1GiftCertificate() throws DataNotFoundException {
        when(mock.getGiftCertificate(GIFT_CERTIFICATE_1.getId())).thenReturn(GIFT_CERTIFICATE_1);
        assertEquals("Actual gift certificate should be equal to expected",
                GIFT_CERTIFICATE_1, service.getGiftCertificatesById(GIFT_CERTIFICATE_1.getId()));
    }

    @Test
    public void getAll_expectedGiftCertificateList_whenGetting1GiftCertificates() throws DataNotFoundException {
        when(mock.getGiftCertificates()).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getAll());
    }

    @Test
    public void getByParameter_expectedGiftCertificateList_whenGettingGiftCertificatesWithNullParams() throws DataNotFoundException, WrongParameterException {
        when(mock.getGiftCertificatesByQuery(null, null)).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getGiftCertificatesByParameter(null, null));
    }

    @Test
    public void getByParameter_expectedGiftCertificateList_whenGettingGiftCertificatesWithParams() throws DataNotFoundException, WrongParameterException {
        HashMap<String, String> params = new HashMap<>();
        params.put(NAME, NAME_VALUE);
        when(mock.getGiftCertificatesByQuery(params, Arrays.asList(NAME, DATE))).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getGiftCertificatesByParameter(params, Arrays.asList(NAME, DATE)));
    }

    @Test
    public void saveGiftCertificate_GiftCertificateWithRightParams_whenSavingCorrectGiftCertificate()
            throws WrongParameterException, ApplicationDatabaseException, DataNotFoundException {
        when(mock.saveGiftCertificate(any())).thenReturn(GIFT_CERTIFICATE_1);
        when(mock.getGiftCertificate(GIFT_CERTIFICATE_1.getId())).thenReturn(GIFT_CERTIFICATE_1);
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
    public void updateGiftCertificate_GiftCertificateWithRightParams_whenUpdatingCorrectGiftCertificate() throws DataNotFoundException, WrongParameterException, ApplicationDatabaseException {
        when(mock.getGiftCertificate(GIFT_CERTIFICATE_2.getId())).thenReturn(GIFT_CERTIFICATE_2_BEFORE_UPDATE);
        when(mock.updateGiftCertificate(any())).thenReturn(GIFT_CERTIFICATE_2);
        when(mockTagService.getTags()).thenReturn(TAG_LIST);
        when(mockTagService.getTagByName(TAG_1.getName())).thenReturn(TAG_1);
        when(mockTagService.getTagByName(TAG_2.getName())).thenReturn(TAG_2);
        when(mockGiftCertificateTagService.getGiftCertificateTags()).thenReturn(TAG_GIFT_LIST);

        GiftCertificate actualGC = service.updateGiftCertificate(GIFT_CERTIFICATE_2.getId(), GIFT_CERTIFICATE_2);

        assertEquals("Actual name should be equal to expected", GIFT_CERTIFICATE_2.getName(), actualGC.getName());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_2.getDescription(), actualGC.getDescription());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_2.getDuration(), actualGC.getDuration());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_2.getTagList(), actualGC.getTagList());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_2.getPrice(), actualGC.getPrice());
        assertNotNull("Created time should be not empty", actualGC.getCreateDate());
        assertNotNull("Last update time should be not empty", actualGC.getLastUpdateDate());
    }
}
