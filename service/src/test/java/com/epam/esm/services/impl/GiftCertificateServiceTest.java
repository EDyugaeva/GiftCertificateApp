package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.model.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.constants.GiftCertificatesTestConstants.*;
import static com.epam.esm.constants.QueryParams.*;
import static org.mockito.ArgumentMatchers.any;
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
    public void getAll_expectedGiftCertificateList_whenGetting1GiftCertificates() {
        when(mock.getGiftCertificates()).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getAll());
    }

    @Test
    public void getByParameter_expectedGiftCertificateList_whenGettingGiftCertificatesWithNullParams() {
        when(mock.getGiftCertificatesByQuery(null, null, null)).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getGiftCertificatesByParameter(null, null, null));
    }

    @Test
    public void getByParameter_expectedGiftCertificateList_whenGettingGiftCertificatesWithParams()  {
        HashMap<String, String> params = new HashMap<>();
        params.put(NAME, NAME_VALUE);
        when(mock.getGiftCertificatesByQuery(params, Arrays.asList(NAME, DATE), DESC)).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getGiftCertificatesByParameter(params, Arrays.asList(NAME, DATE), DESC));
    }

    @Test
    public void saveGiftCertificate_GiftCertificateWithRightParams_whenSavingCorrectGiftCertificate() {
        when(mock.saveGiftCertificate(any())).thenReturn(GIFT_CERTIFICATE_1);
        when(mock.getGiftCertificate(GIFT_CERTIFICATE_1.getId())).thenReturn(GIFT_CERTIFICATE_1);

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

        GiftCertificate actualGC = service.updateGiftCertificate(GIFT_CERTIFICATE_2_BEFORE_UPDATE.getId(), GIFT_CERTIFICATE_2_TO_UPDATE);

        assertEquals("Actual name should be equal to expected", GIFT_CERTIFICATE_2.getName(), actualGC.getName());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_2.getDescription(), actualGC.getDescription());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_2.getDuration(), actualGC.getDuration());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_2.getTagList(), actualGC.getTagList());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_2.getPrice(), actualGC.getPrice());
        assertNotNull("Created time should be not empty", actualGC.getCreateDate());
        assertNotNull("Last update time should be not empty", actualGC.getLastUpdateDate());
    }


}
