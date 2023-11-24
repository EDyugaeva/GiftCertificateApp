package com.epam.esm.services;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.services.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.esm.constants.GiftCertificatesTestConstants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

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


}
