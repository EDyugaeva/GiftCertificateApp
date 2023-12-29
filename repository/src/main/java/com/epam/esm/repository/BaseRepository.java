package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> findById(long id);
    List<T> findAll(int page, int size);
    T save(T entity);
    void deleteById(long id);
}
