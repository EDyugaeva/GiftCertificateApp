package com.epam.esm.dao;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagDao {
    Tag saveTag(Tag tag);

    Tag getTag(long id) throws DataNotFoundException;

    List<Tag> getTags() throws DataNotFoundException;

    void deleteTag(long id);
}
