package com.epam.esm.dao.impl;

import com.epam.esm.dao.Constants;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@Slf4j
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplateObject;
    private static final String INSERT = "insert into Tag (name) values (?)";
    private static final String SELECT_BY_ID = "select * from Tag where id = ?";
    private static final String SELECT_BY_NAME = "select * from Tag where name = ?";
    private static final String SELECT_ALL = "select * from Tag";
    private static final String DELETE = "delete from Tag where id = ?";

    public TagDaoImpl(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public Tag create(Tag tag) {
        log.info("Saving tag " + tag);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{Constants.ID});
                    ps.setString(1, tag.getName());
                    return ps;
                },
                keyHolder);
        tag.setId(keyHolder.getKeyAs(Long.class));
        return tag;
    }

    @Override
    public Tag getById(long id) {
        try {
            log.info("Trying to get tag with id = {}", id);
            return jdbcTemplateObject.queryForObject(SELECT_BY_ID, new TagMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Tag getTagByName(String name) {
        try {
            log.info("Trying to get tag with name = {}", name);
            return jdbcTemplateObject.queryForObject(SELECT_BY_NAME, new TagMapper(), name);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Tag> getAll() {
        try {
            log.info("Trying to get all tags");
            List<Tag> tagList = jdbcTemplateObject.query(SELECT_ALL, new TagMapper());
            if (tagList.isEmpty()) {
                log.warn("Empty tag list tags");
            }
            return tagList;
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        log.info("Trying to delete tag with id = {}", id);
        jdbcTemplateObject.update(DELETE, id);
    }
}
