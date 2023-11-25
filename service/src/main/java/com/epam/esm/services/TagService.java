package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagService {
    Tag saveTag(String name);
    Tag getTag(long id) throws DataNotFoundException;
    List<Tag> getTags();
    void deleteTag(long id);
    Tag getTagByName(String name);
}
