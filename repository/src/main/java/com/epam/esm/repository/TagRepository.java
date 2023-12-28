package com.epam.esm.repository;

import com.epam.esm.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends BaseRepository<Tag> {
    List findAllByNameIn(List<String> tagNames);
    Optional<Tag> findMostUsedTagByUser(Long userId);
}
