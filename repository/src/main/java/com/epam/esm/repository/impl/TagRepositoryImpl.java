package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Slf4j
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext()
    private EntityManager entityManager;
    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List findAll(int page, int size) {
        String hql = "FROM Tag ";
        Query query = entityManager.createQuery(hql, Tag.class)
                .setFirstResult(page * size)
                .setMaxResults(size);
        return query.getResultList();
    }

    @Override
    @Transactional
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
        log.info("Finding tags in names: {}", tagNames);
        String hql = "FROM Tag WHERE name IN :tagNames";
        Query query = entityManager.createQuery(hql, Tag.class)
                .setParameter("tagNames", tagNames);
        return query.getResultList();
    }

    @Override
    public Optional<Tag> findMostUsedTagByUser(Long userId) {
        String sql = "SELECT t.id, t.name\n" +
                "FROM orders o\n" +
                "         JOIN users u ON o.user_id = u.id\n" +
                "         JOIN gift_certificate g ON g.id = o.gift_certificate_id\n" +
                "         JOIN gift_certificate_tag gct ON g.id = gct.gift_id\n" +
                "         JOIN tag t ON t.id = gct.tag_id\n" +
                "WHERE u.id = :userId\n" +
                "group by t.id, t.name\n" +
                "having sum(o.price) = (SELECT max(total_price)\n" +
                "                       FROM (SELECT sum(o.price) AS total_price\n" +
                "                             FROM orders o\n" +
                "                                      JOIN users u ON o.user_id = u.id\n" +
                "                                      JOIN gift_certificate g ON g.id = o.gift_certificate_id\n" +
                "                                      JOIN gift_certificate_tag gct ON g.id = gct.gift_id\n" +
                "                                      JOIN tag t ON t.id = gct.tag_id\n" +
                "                             WHERE u.id = :userId\n" +
                "                             GROUP BY t.id, t.name) subquery)\n" +
                "ORDER BY SUM(o.price) DESC";
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
