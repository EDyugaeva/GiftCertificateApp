package com.epam.esm.services.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import com.epam.esm.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodes.WRONG_PARAMETER;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag saveTag(String name) throws WrongParameterException, OtherDatabaseException {
        log.info("Saving tag with name {}", name);
        Tag tag = new Tag();
        tag.setName(name);
        return tagDao.saveTag(tag);
    }

    @Override
    public Tag getTag(long id) throws DataNotFoundException {
        log.info("Getting tag with id {}", id);
        return tagDao.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) throws DataNotFoundException {
        log.info("Getting tag with name {}", name);
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> getTags() throws DataNotFoundException {
        log.info("Getting all tags");
        return tagDao.getTags();
    }

    @Override
    public void deleteTag(long id) throws WrongParameterException {
        log.info("Deleting tag with id {}", id);
        try {
            getTag(id);
            tagDao.deleteTag(id);
        } catch (Exception e) {
            throw new WrongParameterException(e.getMessage(), WRONG_PARAMETER);
        }
    }
}
