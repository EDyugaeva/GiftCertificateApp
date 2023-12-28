package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List findAll(Pageable pageable) {
        String hql = "FROM Tag ";
        Query query = entityManager.createQuery(hql, Tag.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    @Override
    public Tag save(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void deleteById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            entityManager.remove(tag);
        }
    }

    @Override
    public List findAllByNameIn(List<String> tagNames) {
        String hql = "FROM Tag WHERE name IN :tagNames";
        Query query = entityManager.createQuery(hql, Tag.class)
                .setParameter("tagNames", tagNames);
        return query.getResultList();
    }

    @Override
    public Optional<Tag> findMostUsedTagByUser(Long userId) {
        // Implement the custom native query using Hibernate
        String sql = "SELECT t.id, t.name " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "JOIN gift_certificate g ON g.id = o.gift_certificate_id " +
                "JOIN gift_certificate_tag gct ON g.id = gct.gift_id " +
                "JOIN tag t ON t.id = gct.tag_id " +
                "WHERE u.id = :userId " +
                "GROUP BY t.name, t.id " +
                "ORDER BY SUM(o.price) DESC " +
                "LIMIT 1";
        Query query = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId);
        List resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            Object[] result = (Object[]) resultList.get(0);
            Long tagId = ((Number) result[0]).longValue();
            String tagName = (String) result[1];
            return Optional.of(new Tag(tagId, tagName));
        } else {
            return Optional.empty();
        }
    }
}
