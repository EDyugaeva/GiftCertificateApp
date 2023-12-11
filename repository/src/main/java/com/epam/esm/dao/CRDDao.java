package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface CRDDao<T> {
    T create(T object);
    Optional<T> getById(long id);
    List<T> getAll();
    void deleteById(long id)  ;
}
