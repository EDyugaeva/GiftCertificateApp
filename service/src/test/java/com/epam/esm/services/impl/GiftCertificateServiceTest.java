package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.services.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.constants.TestConstants.*;
import static com.epam.esm.constants.TestConstants.GiftCertificatesTestConstants.*;
import static com.epam.esm.constants.TestConstants.TagTestConstants.TAG_1;
import static com.epam.esm.constants.TestConstants.TagTestConstants.TAG_SET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateRepository mock;
    @Mock
    private TagService tagService;
    @InjectMocks
    private GiftCertificateServiceImpl service;

    @Test
    public void getGiftCertificate_expectedGiftCertificate_whenGetting1GiftCertificate() throws DataNotFoundException {
        when(mock.findById(GIFT_CERTIFICATE_1.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        assertEquals("Actual gift certificate should be equal to expected",
                GIFT_CERTIFICATE_1, service.getGiftCertificatesById(GIFT_CERTIFICATE_1.getId()));
    }

    @Test
    public void getAll_expectedGiftCertificateList_whenGetting1GiftCertificates() throws DataNotFoundException {
        when(mock.findAll(PAGE, SIZE)).thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getAll(PAGE, SIZE));
    }

    @Test
    public void getByParameters_expectedGiftCertificateList_whenGettingGiftCertificatesWithParams()
            throws WrongParameterException, DataNotFoundException {
        when(mock.findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTagSet_Name(GIFT_CERTIFICATE_1.getName(),
                GIFT_CERTIFICATE_1.getDescription(), TAG_1.getName(), PAGE, SIZE, SORT))
                .thenReturn(GIFT_CERTIFICATE_LIST);
        assertEquals("Actual gift certificate list should be equal to expected",
                GIFT_CERTIFICATE_LIST, service.getGiftCertificatesByParameters(PAGE, SIZE, GIFT_CERTIFICATE_1.getName(),
                        GIFT_CERTIFICATE_1.getDescription(), Optional.ofNullable(TAG_1.getName()), SORT));

        verify(mock, times(1))
                .findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTagSet_Name(GIFT_CERTIFICATE_1.getName(),
                        GIFT_CERTIFICATE_1.getDescription(), TAG_1.getName(), PAGE, SIZE, SORT);
        verify(mock, times(0))
                .findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(GIFT_CERTIFICATE_1.getName(),
                        GIFT_CERTIFICATE_1.getDescription(), PAGE, SIZE, SORT);

    }

    @Test
    public void saveGiftCertificate_GiftCertificateWithRightParams_whenSavingCorrectGiftCertificate()
            throws WrongParameterException {
        when(mock.save(any())).thenReturn(GIFT_CERTIFICATE_1);
        when(tagService.findAllByNameIn(TAG_SET.stream().map(Tag::getName).collect(Collectors.toList()))).thenReturn(TAG_SET);

        GiftCertificate actualGC = service.saveGiftCertificate(GIFT_CERTIFICATE_1);

        assertEquals("Actual name should be equal to expected", GIFT_CERTIFICATE_1.getName(), actualGC.getName());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_1.getDescription(), actualGC.getDescription());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_1.getDuration(), actualGC.getDuration());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_1.getTagSet(), actualGC.getTagSet());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_1.getPrice(), actualGC.getPrice());
        assertNotNull("Created time should be not empty", actualGC.getCreateDate());
        assertNull("Last update time should be empty", actualGC.getLastUpdateDate());

        verify(tagService, times(0)).saveTag(any());
    }

    @Test
    public void updateGiftCertificate_GiftCertificateWithRightParams_whenUpdatingCorrectGiftCertificate()
            throws DataNotFoundException, WrongParameterException {
        when(mock.findById(GIFT_CERTIFICATE_2.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        when(mock.save(any())).thenReturn(GIFT_CERTIFICATE_2);
        when(tagService.findAllByNameIn(TAG_SET.stream().map(Tag::getName).collect(Collectors.toList()))).thenReturn(TAG_SET);

        GiftCertificate actualGC = service.updateGiftCertificate(GIFT_CERTIFICATE_2.getId(), GIFT_CERTIFICATE_2);

        assertEquals("Actual name should be equal to expected", GIFT_CERTIFICATE_2.getName(), actualGC.getName());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_2.getDescription(), actualGC.getDescription());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_2.getDuration(), actualGC.getDuration());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_2.getTagSet(), actualGC.getTagSet());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_2.getPrice(), actualGC.getPrice());
        assertNotNull("Created time should be not empty", actualGC.getCreateDate());
        assertNotNull("Last update time should be not empty", actualGC.getLastUpdateDate());

        verify(tagService, times(0)).saveTag(any());
    }

    @Test
    public void updateGiftCertificateDuration_GiftCertificateWithRightParams_whenUpdatingCorrectDuration()
            throws DataNotFoundException, WrongParameterException {
        when(mock.findById(GIFT_CERTIFICATE_3.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_3));
        when(mock.save(any())).thenReturn(GIFT_CERTIFICATE_3);

        GiftCertificate actualGC = service.updateGiftCertificateDuration(GIFT_CERTIFICATE_3.getId(), POSITIVE_DURATION);

        assertEquals("Actual name should be equal to expected", GIFT_CERTIFICATE_3.getName(), actualGC.getName());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_3.getDescription(), actualGC.getDescription());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_3.getDuration(), actualGC.getDuration());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_3.getTagSet(), actualGC.getTagSet());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_3.getPrice(), actualGC.getPrice());
        assertNotNull("Created time should be not empty", actualGC.getCreateDate());
        assertNotNull("Last update time should be not empty", actualGC.getLastUpdateDate());

        verify(tagService, times(0)).saveTag(any());
    }

    @Test
    public void updateGiftCertificatePrice_GiftCertificateWithRightParams_whenUpdatingCorrectPrice()
            throws DataNotFoundException, WrongParameterException {
        when(mock.findById(GIFT_CERTIFICATE_3.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_3));
        when(mock.save(any())).thenReturn(GIFT_CERTIFICATE_3);

        GiftCertificate actualGC = service.updateGiftCertificatePrice(GIFT_CERTIFICATE_3.getId(), POSITIVE_PRICE);

        assertEquals("Actual name should be equal to expected", GIFT_CERTIFICATE_3.getName(), actualGC.getName());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_3.getDescription(), actualGC.getDescription());
        assertEquals("Actual description should be equal to expected", GIFT_CERTIFICATE_3.getDuration(), actualGC.getDuration());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_3.getTagSet(), actualGC.getTagSet());
        assertEquals("Actual tag list should be equal to expected", GIFT_CERTIFICATE_3.getPrice(), actualGC.getPrice());
        assertNotNull("Created time should be not empty", actualGC.getCreateDate());
        assertNotNull("Last update time should be not empty", actualGC.getLastUpdateDate());

        verify(tagService, times(0)).saveTag(any());
    }
}
