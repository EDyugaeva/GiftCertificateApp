package com.epam.esm.services.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.esm.constants.TagTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagDaoImpl mock = Mockito.mock(TagDaoImpl.class);

    @InjectMocks
    private TagServiceImpl service;

    @Test
    public void getTag_expectedTag_whenGettingTag() throws DataNotFoundException, OtherDatabaseException {
        when(mock.getTag(TAG_1.getId())).thenReturn(TAG_1);
        assertEquals("Actual tag should be equal to expected", TAG_1, service.getTag(TAG_1.getId()));
    }

    @Test
    public void getTag_exception_whenGettingException() throws DataNotFoundException {
        when(mock.getTag(TAG_1.getId())).thenThrow(DataNotFoundException.class);
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> service.getTag(TAG_1.getId()),
                "Tag should be not found and DataNotFoundException should be thrown");
    }

    @Test
    public void getTags_expectedTagList_whenGettingTags() throws DataNotFoundException {
        when(mock.getTags()).thenReturn(TAG_LIST);
        assertEquals("Actual tag list should be equal to expected", TAG_LIST, service.getTags());
    }

    @Test
    public void getTagByName_expectedTag_whenGettingTagByName() throws DataNotFoundException {
        when(mock.getTagByName(TAG_1.getName())).thenReturn(TAG_1);
        assertEquals("Actual tag should be equal to expected", TAG_1, service.getTagByName(TAG_1.getName()));
    }

    @Test
    public void saveTag_expectedTag_whenSavingTag() throws WrongParameterException, OtherDatabaseException {
        Tag savingTag = new Tag();
        savingTag.setName(TAG_2.getName());
        when(mock.saveTag(savingTag)).thenReturn(TAG_2);
        assertEquals("Actual tag should be equal to expected", TAG_2, service.saveTag(TAG_2.getName()));
    }
}
