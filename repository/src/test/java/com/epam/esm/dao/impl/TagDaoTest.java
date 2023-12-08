package com.epam.esm.dao.impl;

import com.epam.esm.config.AppTestConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.epam.esm.constants.TagTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagDaoTest extends BaseTest {

    @Autowired
    private TagDao tagDao;

    @Test
    @Order(1)
    public void getTags_correctTagList_whenGetTags() {
        assertEquals(TAG_LIST, tagDao.getAll(), "When getting tags, tag list should be equal to database values");
    }

    @Test
    public void getTag_correctTag_whenGetTag() {
        assertEquals(Optional.of(TAG_1) , tagDao.getById(TAG_1.getId()), "When getting tag, tag should be equal to database value");
    }

    @Test
    public void getTagByName_correctTag_whenGetTagByName() {
        assertEquals(Optional.of(TAG_1), tagDao.getTagByName(TAG_1.getName()), "When getting tag, tag should be equal to database value");
    }

    @Test
    public void getTag_EmptyOptional_whenTryToGetAbsentTag() {
        assertEquals(Optional.empty(),tagDao.getById(ABSENT_ID),
                "Tag should be not found and null should be return");
    }

    @Test
    public void saveTag_savedTag_whenTagWasSaved() {
        Tag savingTag = NEW_TAG;
        tagDao.create(savingTag);
        savingTag.setId(NEW_ID);
        assertEquals(Optional.of(savingTag), tagDao.getById(NEW_ID), "Tag should be saved with correct ID");
    }

    @Test
    public void deleteTag_EmptyOptional_whenTryToGetDeletedTag() {
        tagDao.deleteById(TAG_5.getId());
        assertEquals(Optional.empty(), tagDao.getById(TAG_5.getId()),
                "Tag should be not found and null should be return");
    }
}



