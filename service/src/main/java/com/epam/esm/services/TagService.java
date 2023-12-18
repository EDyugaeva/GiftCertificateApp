package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagService {
    Tag saveTag(Tag tag) throws WrongParameterException;
    Tag getTag(long id) throws DataNotFoundException;
    List<Tag> getTags(Pageable pageable) throws DataNotFoundException;
    void deleteTag(long id) throws WrongParameterException;
    Tag getTagByName(String name) throws DataNotFoundException;

    Optional<Set<Tag>> findAllByNameIn(List<String> tagNames);

    Tag findMostUsedTagByUser(Long userId) throws DataNotFoundException;
}
