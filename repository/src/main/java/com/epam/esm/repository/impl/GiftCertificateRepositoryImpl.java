package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    @PersistenceContext()
    private EntityManager entityManager;
    @Override
    public List findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTagSet_Name(
            String name, String description, String tagName, int page, int size) {
        String hql = "FROM GiftCertificate gc  "  +
                " WHERE LOWER(gc.name) LIKE LOWER(:name) " +
                "AND LOWER(gc.description) LIKE LOWER(:description) " +
                "AND :tagName IN (SELECT tag.name FROM gc.tagSet tag)";
        Query query = entityManager.createQuery(hql, GiftCertificate.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("description", "%" + description + "%")
                .setParameter("tagName", tagName)
                .setFirstResult(page *size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
            String name, String description, int page, int size) {
        String hql = "FROM GiftCertificate WHERE LOWER(name) LIKE LOWER(:name) " +
                "AND LOWER(description) LIKE LOWER(:description)";
        Query query = entityManager.createQuery(hql, GiftCertificate.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("description", "%" + description + "%")
                .setFirstResult(page *size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List findByTagSet_NameIn(List<String> names, int page, int size) {
        String hql = "FROM GiftCertificate gc join Tag t WHERE t.name IN :names";
        Query query = entityManager.createQuery(hql, GiftCertificate.class)
                .setParameter("names", names)
                .setFirstResult(page *size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List findAll(int page, int size) {
        String hql = "FROM GiftCertificate";
        Query query = entityManager.createQuery(hql, GiftCertificate.class)
                .setFirstResult(page *size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public GiftCertificate save(GiftCertificate entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void deleteById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate != null) {
            entityManager.remove(giftCertificate);
        }
    }
}
