package com.epam.esm.repository.impl;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        String hql = "FROM User";
        Query query = entityManager.createQuery(hql, User.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }
    @Override
    public List<Order> findOrdersByUserId(Long id, Pageable pageable) {
        String hql = "SELECT o FROM Order o JOIN o.user u WHERE u.id = :userId";
        Query query = entityManager.createQuery(hql, Order.class)
                .setParameter("userId", id)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());
        List<Order> resultList = query.getResultList();
        return resultList;
    }
}
