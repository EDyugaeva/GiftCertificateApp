package com.epam.esm.repository;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select o from orders o join users u on o.user.id = u.id where u.id = :id")
    Page<Order> findOrdersByUserId(@Param("id") Long id, Pageable pageable);
}
