package com.epam.esm.services.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import com.epam.esm.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_FOUND_TAG;
import static com.epam.esm.exceptions.ExceptionCodesConstants.WRONG_PARAMETER;

/**
 * Implementation of the {@link TagService} interface that provides
 * CRUD operations for managing tags.
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public Tag saveTag(String name) throws WrongParameterException {
        try {
            log.info("Saving tag with name {}", name);
            Tag tag = new Tag();
            tag.setName(name);
            return tagDao.create(tag);
        } catch (DuplicateKeyException e) {
            throw new WrongParameterException("Wrong parameter in request", WRONG_PARAMETER);
        }
    }

    @Override
    public Tag getTag(long id) throws DataNotFoundException {
        log.info("Getting tag with id {}", id);
        Tag tag = tagDao.getById(id);
        if (tag != null) {
            return tag;
        }
        log.warn("Tag was not found");
        throw new DataNotFoundException(String.format("Requested resource was not found (id = %d)", id), NOT_FOUND_TAG);
    }

    @Override
    public Tag getTagByName(String name) throws DataNotFoundException {
        log.info("Getting tag with name {}", name);
        Tag tag = tagDao.getTagByName(name);
        if (tag != null) {
            return tag;
        }
        log.warn("Tag was not found");
        throw new DataNotFoundException(String.format("Requested resource was not found (name = %s)", name),
                NOT_FOUND_TAG);
    }

    @Override
    public List<Tag> getTags() throws DataNotFoundException {
        log.info("Getting all tags");
        List<Tag> tagList = tagDao.getAll();
        if (tagList != null) {
            return tagList;
        }
        log.warn("Tags were not found");
        throw new DataNotFoundException("Requested resource was not found (tags)", NOT_FOUND_TAG);
    }

    @Override
    @Transactional
    public void deleteTag(long id) throws WrongParameterException {
        log.info("Deleting tag with id {}", id);
        try {
            getTag(id);
            tagDao.deleteById(id);
        } catch (DataNotFoundException e) {
            log.error("Exception while deleting tag with id = {}", id, e);
            throw new WrongParameterException("Exception while deleting tag", WRONG_PARAMETER);
        }
    }
}
