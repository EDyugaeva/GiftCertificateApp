package com.epam.esm.dao.impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.constants.TagTestConstants.*;
import static com.epam.esm.exceptions.ExceptionCodes.NOT_FOUND_TAG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @Test
    public void getTags_correctTagList_whenGetTags() {
        assertEquals(TAG_LIST, tagDao.getTags(), "When getting tags, tag list should be equal to database values");
    }

    @Test
    public void getTag_correctTag_whenGetTag() {
        assertEquals(TAG_1, tagDao.getTag(TAG_1.getId()), "When getting tag, tag should be equal to database value");
    }

    @Test
    public void getTag_exception_whenTryToGetAbsentTag() {
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> tagDao.getTag(ABSENT_ID),
                "Tag should be not found and  DataNotFoundException should be thrown");
        assertEquals(NOT_FOUND_TAG.getErrorCode(), exception.getMessage(), "Exception message should be " + NOT_FOUND_TAG.getErrorCode());
    }

    @Test
    public void saveTag_savedTag_whenTagWasSaved() {
        Tag savingTag = NEW_TAG;
        tagDao.saveTag(savingTag);
        savingTag.setId(NEW_ID);
        assertEquals(savingTag, tagDao.getTag(NEW_ID), "Tag should be saved with correct ID");
    }

    @Test
    public void deleteTag_Exception_whenTryToGetDeletedTag() {
        tagDao.deleteTag(TAG_1.getId());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> tagDao.getTag(TAG_1.getId()),
                "Tag should be not found and  DataNotFoundException should be thrown");
        assertEquals(NOT_FOUND_TAG.getErrorCode(), exception.getMessage(), "Exception message should be " + NOT_FOUND_TAG.getErrorCode());
    }
}



