package com.epam.esm.services.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import com.epam.esm.services.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag saveTag(String name) {
        logger.info("Saving tag with name {}", name);
        Tag tag = new Tag();
        tag.setName(name);
        return tagDao.saveTag(tag);
    }

    @Override
    public Tag getTag(long id) {
        logger.info("Getting tag with id {}", id);
        return tagDao.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        logger.info("Getting tag with name {}", name);
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> getTags() {
        logger.info("Getting all tags");
        return tagDao.getTags();
    }

    @Override
    public void deleteTag(long id) {
        logger.info("Deleting tag with id {}", id);
        tagDao.deleteTag(id);
    }
}
