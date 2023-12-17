package com.epam.esm.repository;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    List<Order> findAllByUserId(Long id, Pageable pageable);
}
