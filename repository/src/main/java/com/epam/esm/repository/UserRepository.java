package com.epam.esm.repository;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository{
    Optional<User> findById(Long id);

    List<User> findAll(Pageable pageable);

    List<Order> findOrdersByUserId(Long userId, Pageable pageable);
}
