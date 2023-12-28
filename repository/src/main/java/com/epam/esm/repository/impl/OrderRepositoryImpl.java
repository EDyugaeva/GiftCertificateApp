package com.epam.esm.repository.impl;

import com.epam.esm.model.Order;
import com.epam.esm.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List findAll(int page, int size) {
        String hql = "FROM orders ";
        Query query = entityManager.createQuery(hql, Order.class)
                .setFirstResult(page * size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Order save(Order entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            entityManager.remove(order);
        }
    }
}
