package com.epam.esm.dao.impl;

import com.epam.esm.config.AppTestConfig;
import com.epam.esm.model.GiftCertificateTag;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.constants.GiftCertificateTagTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiftCertificateTagDaoImplTest  extends BaseTest{
    @Autowired
    private GiftCertificateTagDaoImpl dao;

    @Test
    @Order(1)
    public void getGiftCertificateTags_correctList_whenGetAllPairs()   {
        assertEquals(TAG_GIFT_LIST, dao.getAll(), "When getting tags gifts pair, their list should be equal to database values");
    }

    @Test
    public void getGiftCertificateTag_correctPair_whenGetGiftTagsPair()   {
        assertEquals(GIFT_TAG_1, dao.getById(GIFT_TAG_1.getId()), "When getting tags gifts pair, it should be equal to database value");
    }

    @Test
    public void getGiftCertificateTag_Null_whenTryToGetAbsentGiftCertificateTag() {
       assertNull(dao.getById(ABSENT_ID),
                "Pair should be not found and null should be return");
    }

    @Test
    public void saveGiftTag_savedGiftTag_whenGiftTagWasSaved()  {
        GiftCertificateTag savingPair = GIFT_TAG_NEW;
        dao.create(savingPair);
        savingPair.setId(NEW_ID);
        assertEquals(savingPair, dao.getById(NEW_ID), "Gift tag pair should be saved with correct ID");
    }

    @Test
    public void deleteGiftTag_Null_whenTryToGetDeletedGiftTag()  {
        dao.deleteById(GIFT_TAG_5.getId());
        assertNull(dao.getById(GIFT_TAG_5.getId()),
                "Gift tag pair should be not found and null should be return");
    }
}
