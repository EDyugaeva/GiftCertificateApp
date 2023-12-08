package com.epam.esm.dao;

import java.util.List;

public interface CRDDao<T> {
    T create(T object);
    T getById(long id);
    List<T> getAll();
    void deleteById(long id)  ;
}
