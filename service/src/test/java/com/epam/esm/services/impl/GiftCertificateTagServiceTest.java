package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.impl.GiftCertificateTagDaoImpl;
import com.epam.esm.model.GiftCertificateTag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.esm.constants.GiftCertificateTagTestConstants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateTagServiceTest {

    @Mock
    private GiftCertificateTagDao mock = Mockito.mock(GiftCertificateTagDaoImpl.class);

    @InjectMocks
    private GiftCertificateTagServiceImpl service;

    @Test
    public void getGiftCertificateTag_expectedGiftCertificateTag_whenGettingGiftCertificateTag() {
        when(mock.getGiftCertificateTag(GIFT_TAG_1.getId())).thenReturn(GIFT_TAG_1);
        assertEquals("Actual gift-certificate-tag should be equal to expected", GIFT_TAG_1, service.getGiftCertificateTag(GIFT_TAG_1.getId()));
    }

    @Test
    public void getGiftCertificatesTags_expectedGiftCertificateTagList_whenGettingGiftCertificateTags() {
        when(mock.getGiftCertificateTags()).thenReturn(TAG_GIFT_LIST);
        assertEquals("Actual gift-certificate-tag list should be equal to expected", TAG_GIFT_LIST, service.getGiftCertificateTags());
    }

    @Test
    public void saveGiftCertificateTag_expectedGiftCertificateTag_whenSavingGiftCertificateTag() {
        GiftCertificateTag saving = new GiftCertificateTag();
        saving.setTagId(GIFT_TAG_2.getTagId());
        saving.setGiftCertificateId(GIFT_TAG_2.getGiftCertificateId());
        when(mock.saveGiftTag(saving)).thenReturn(GIFT_TAG_2);
        assertEquals("Actual gift-certificate-tag should be equal to expected",
                GIFT_TAG_2, service.saveGiftCertificateTag(GIFT_TAG_2.getGiftCertificateId(), GIFT_TAG_2.getTagId()));
    }
}
