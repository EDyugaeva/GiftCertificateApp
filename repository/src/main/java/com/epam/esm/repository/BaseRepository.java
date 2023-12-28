package com.epam.esm.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> findById(long id);
    List<T> findAll(Pageable pageable);
    T save(T entity);
    void deleteById(long id);
}
