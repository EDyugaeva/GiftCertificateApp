package com.epam.esm.dao.impl;

import com.epam.esm.dao.Column;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodesConstants.*;

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
    public Tag saveTag(Tag tag) throws WrongParameterException, OtherDatabaseException {
        log.info("Saving tag " + tag);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplateObject.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{Column.ID});
                        ps.setString(1, tag.getName());
                        return ps;
                    },
                    keyHolder);
            tag.setId(keyHolder.getKeyAs(Long.class));
            return tag;
        } catch (EmptyResultDataAccessException | DuplicateKeyException e) {
            log.error("Exception while saving new tag");
            throw new WrongParameterException("Parameters are not correct", WRONG_PARAMETER);
        } catch (Exception e) {
            log.error("Exception while saving new  tag ");
            throw new OtherDatabaseException("Exception while saving new   tag ", OTHER_EXCEPTION);
        }
    }

    @Override
    public Tag getTag(long id) throws DataNotFoundException {
        try {
            log.info("Trying to get tag with id = {}", id);
            return jdbcTemplateObject.queryForObject(SELECT_BY_ID, new Object[]{id}, new TagMapper());
        } catch (RuntimeException e) {
            log.error("Error while getting tag with id = {}", id, e);
            throw new DataNotFoundException(String.format("Requested resource not found (id = %d)", id), NOT_FOUND_TAG);
        }
    }

    @Override
    public Tag getTagByName(String name) throws DataNotFoundException {
        try {
            log.info("Trying to get tag with name = {}", name);
            return jdbcTemplateObject.queryForObject(SELECT_BY_NAME, new Object[]{name}, new TagMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting tag with name = {}", name, e);
            throw new DataNotFoundException(String.format("Requested resource not found (name = %s)", name), NOT_FOUND_TAG);
        }
    }

    @Override
    public List<Tag> getTags()  {
            log.info("Trying to get all tags");
            List<Tag> tagList = jdbcTemplateObject.query(SELECT_ALL, new TagMapper());
        if (tagList.isEmpty()) {
            log.warn("Empty tag list tags");
        }
        return tagList;
    }

    @Override
    public void deleteTag(long id) throws OtherDatabaseException {
        log.info("Trying to delete tag with id = {}", id);
        try {
            jdbcTemplateObject.update(DELETE, id);
        } catch (RuntimeException e) {
            log.error("Exception while deleting tag with id = {}", id, e);
            throw new OtherDatabaseException("Exception while deleting tag", OTHER_EXCEPTION);
        }
    }
}
