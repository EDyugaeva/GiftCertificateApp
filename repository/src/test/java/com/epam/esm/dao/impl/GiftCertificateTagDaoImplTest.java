package com.epam.esm.dao.impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.GiftCertificateTag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.constants.GiftCertificateTagTestConstants.*;
import static com.epam.esm.exceptions.ExceptionCodes.NOT_FOUND_PAIR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GiftCertificateTagDaoImplTest {
    @Autowired
    private GiftCertificateTagDaoImpl dao;

    @Test
    public void getGiftCertificateTags_correctList_whenGetAllPairs() {
        assertEquals(TAG_GIFT_LIST, dao.getGiftCertificateTags(), "When getting tags gifts pair, their list should be equal to database values");
    }

    @Test
    public void getGiftCertificateTag_correctPair_whenGetGiftTagsPair() {
        assertEquals(GIFT_TAG_1, dao.getGiftCertificateTag(GIFT_TAG_1.getId()), "When getting tags gifts pair, it should be equal to database value");
    }

    @Test
    public void getGiftCertificateTag_exception_whenTryToGetAbsentGiftCertificateTag() {
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> dao.getGiftCertificateTag(ABSENT_ID),
                "Pair should be not found and  DataNotFoundException should be thrown");
        assertEquals(NOT_FOUND_PAIR.getErrorCode(), exception.getMessage(), "Exception message should be " + NOT_FOUND_PAIR.getErrorCode());
    }

    @Test
    public void saveGiftTag_savedGiftTag_whenGiftTagWasSaved() {
        GiftCertificateTag savingPair = GIFT_TAG_NEW;
        dao.saveGiftTag(savingPair);
        savingPair.setId(NEW_ID);
        assertEquals(savingPair, dao.getGiftCertificateTag(NEW_ID), "Gift tag pair should be saved with correct ID");
    }

    @Test
    public void deleteGiftTag_Exception_whenTryToGetDeletedGiftTag() {
        dao.deleteGiftTag(GIFT_TAG_1.getId());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> dao.getGiftCertificateTag(GIFT_TAG_1.getId()),
                "Gift tag pair should be not found and  DataNotFoundException should be thrown");
        assertEquals(NOT_FOUND_PAIR.getErrorCode(), exception.getMessage(), "Exception message should be " + NOT_FOUND_PAIR.getErrorCode());
    }
}
