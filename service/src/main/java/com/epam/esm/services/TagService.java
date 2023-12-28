package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Tag saveTag(Tag tag) throws WrongParameterException;
    Tag getTag(long id) throws DataNotFoundException;
    List<Tag> getTags(int page, int size) throws DataNotFoundException;
    void deleteTag(long id) throws WrongParameterException;
    Set<Tag> findAllByNameIn(List<String> tagNames);
    Tag findMostUsedTagByUser(Long userId) throws DataNotFoundException;
}
