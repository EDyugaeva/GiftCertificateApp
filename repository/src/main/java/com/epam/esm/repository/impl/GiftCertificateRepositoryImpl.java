package com.epam.esm.repository.impl;

import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionCodesConstants.WRONG_PARAMETER;

@Repository
@Slf4j
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private static final String DELIMITER = ":";
    private static final int SORTING_TYPE_INDEX = 1;
    private static final int SORTING_VALUE_INDEX = 0;
    public static final String NAME = "name";
    private static final String DATE_SQL = "create_date";
    private static final String DATE = "date";
    private static final String ID = "id";
    @PersistenceContext()
    private EntityManager entityManager;

    @Override
    public List findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTagSet_Name(
            String name, String description, String tagName, int page, int size, String[] sort) {
        String sortingParams = getSortExpression(sort);
        String hql = "FROM GiftCertificate gc  " +
                " WHERE LOWER(gc.name) LIKE LOWER(:name) " +
                "AND LOWER(gc.description) LIKE LOWER(:description) " +
                "AND :tagName IN (SELECT tag.name FROM gc.tagSet tag) " +
                "ORDER BY " + sortingParams;
        Query query = entityManager.createQuery(hql, GiftCertificate.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("description", "%" + description + "%")
                .setParameter("tagName", tagName)
                .setFirstResult(page * size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
            String name, String description, int page, int size, String[] sort) {
        String sortingParams = getSortExpression(sort);
        String hql = "FROM GiftCertificate WHERE LOWER(name) LIKE LOWER(:name) " +
                "AND LOWER(description) LIKE LOWER(:description) " +
                "ORDER BY " + sortingParams;
        Query query = entityManager.createQuery(hql, GiftCertificate.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("description", "%" + description + "%")
                .setFirstResult(page * size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List findByTagSet_NameIn(List<String> names, int page, int size) {
        String hql = "FROM GiftCertificate gc join Tag t WHERE t.name IN :names";
        Query query = entityManager.createQuery(hql, GiftCertificate.class)
                .setParameter("names", names)
                .setFirstResult(page * size)
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
                .setFirstResult(page * size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public GiftCertificate save(GiftCertificate entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate entity) {
        return entity;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate != null) {
            entityManager.remove(giftCertificate);
        }
    }

    private String getSortExpression(String[] sort) {
        StringBuilder sortOrder = new StringBuilder();
        for (int i = 0; i < sort.length; i++) {
            if (sort[i].split(DELIMITER)[SORTING_VALUE_INDEX].equalsIgnoreCase(NAME)) {
                sortOrder.append(NAME);
            } else if (sort[i].split(DELIMITER)[SORTING_VALUE_INDEX].equalsIgnoreCase(DATE)) {
                sortOrder.append(DATE_SQL);
            } else if (sort[i].split(DELIMITER)[SORTING_VALUE_INDEX].equalsIgnoreCase(ID)) {
                sortOrder.append(ID);
            } else {
                log.warn("Exception while getting sorting exception with values: {}", sort);
                throw new WrongParameterException("sorting gift certificates", WRONG_PARAMETER);
            }
            sortOrder.append(" ");
            sortOrder.append(sort[i].split(DELIMITER)[SORTING_TYPE_INDEX]);
            if (i != sort.length - 1) {
                sortOrder.append(", ");
            }
        }
        return sortOrder.toString();
    }
}
