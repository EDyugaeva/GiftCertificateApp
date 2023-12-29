package com.epam.esm.repository;

import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag> {
    List findAllByNameIn(List<String> tagNames);
    Optional<Tag> findMostUsedTagByUser(Long userId);
}
