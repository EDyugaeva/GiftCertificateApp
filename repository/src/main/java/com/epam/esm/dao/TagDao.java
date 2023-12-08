package com.epam.esm.dao;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ApplicationDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagDao {
    Tag saveTag(Tag tag) throws WrongParameterException, ApplicationDatabaseException;
    Tag getTag(long id) throws DataNotFoundException;
    Tag getTagByName(String name) throws DataNotFoundException;
    List<Tag> getTags();
    void deleteTag(long id) throws ApplicationDatabaseException;
}
