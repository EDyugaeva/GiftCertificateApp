package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ApplicationException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagService {
    Tag saveTag(String name) throws WrongParameterException, ApplicationException;
    Tag getTag(long id) throws DataNotFoundException;
    List<Tag> getTags() throws DataNotFoundException;
    void deleteTag(long id) throws WrongParameterException;
    Tag getTagByName(String name) throws DataNotFoundException;
}
