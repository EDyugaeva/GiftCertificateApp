package com.epam.esm.repository;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository{
    Optional<User> findById(Long id);

    List<User> findAll(int page, int size);

    List<Order> findOrdersByUserId(Long userId, int page, int size);
}
