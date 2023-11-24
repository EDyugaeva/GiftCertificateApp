package com.epam.esm.services;

import com.epam.esm.model.Tag;

import java.util.List;

public interface TagService {
    Tag saveTag(String name);

    Tag getTag(long id);
    List<Tag> getTags();

    void deleteTag(long id);
}
