package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.constants.TestConstants.*;
import static com.epam.esm.constants.TestConstants.TagTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagRepository mock;

    @InjectMocks
    private TagServiceImpl service;

    @Test
    public void getTag_expectedTag_whenGettingTag() throws DataNotFoundException {
        when(mock.findById(TAG_1.getId())).thenReturn(Optional.of(TAG_1));
        assertEquals("Actual tag should be equal to expected", TAG_1, service.getTag(TAG_1.getId()));
    }

    @Test
    public void getTag_exception_whenGettingException() {
        when(mock.findById(TAG_1.getId())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> service.getTag(TAG_1.getId()),
                "Tag should be not found and DataNotFoundException should be thrown");
    }

    @Test
    public void getTags_expectedTagList_whenGettingTags() throws DataNotFoundException {
        when(mock.findAll(PAGE, SIZE)).thenReturn(TAG_LIST);
        assertEquals("Actual tag set should be equal to expected", new ArrayList(TAG_SET), service.getTags(PAGE, SIZE));
    }

    @Test
    public void saveTag_expectedTag_whenSavingTag() throws WrongParameterException {
        Tag savingTag = new Tag();
        savingTag.setName(TAG_2.getName());
        when(mock.save(savingTag)).thenReturn(TAG_2);
        assertEquals("Actual tag should be equal to expected", TAG_2, service.saveTag(TAG_2));
    }

    @Test
    public void findAllByNameIn_expectedTagList_whenGettingTags() throws DataNotFoundException {
        List<String> tagNames = Arrays.asList(TAG_1.getName(), TAG_2.getName());
        when(mock.findAllByNameIn(tagNames)).thenReturn(TAG_LIST);
        assertEquals("Actual tag set should be equal to expected", TAG_SET, service.findAllByNameIn(tagNames));
    }
}
