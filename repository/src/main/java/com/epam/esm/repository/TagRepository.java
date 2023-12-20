package com.epam.esm.repository;

import com.epam.esm.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Set<Tag> findAllByNameIn(List<String> tagNames);
    @Query(value = "select t.id, t.name " +
            "from orders o " +
            "         join users u on o.user_id = u.id" +
            "         join gift_certificate g on g.id = o.gift_certificate_id" +
            "         join gift_certificate_tag gct on g.id = gct.gift_id" +
            "         join tag t on t.id = gct.tag_id " +
            "where u.id = ?1 " +
            "group by t.name, t.id " +
            "order by sum(o.price) desc " +
            "limit 1", nativeQuery = true)
    Optional<Tag> findMostUsedTagByUser(Long userId);
}
