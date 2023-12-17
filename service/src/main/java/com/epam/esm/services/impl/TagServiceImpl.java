package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_FOUND_TAG;
import static com.epam.esm.exceptions.ExceptionCodesConstants.WRONG_PARAMETER;

/**
 * Implementation of the {@link TagService} interface that provides
 * CRUD operations for managing tags.
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {
    private final TagRepository repository;

    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Tag saveTag(Tag tag) throws WrongParameterException {
        try {
            log.info("Saving tag {}", tag);
            return repository.save(tag);
        } catch (DuplicateKeyException e) {
            throw new WrongParameterException("Wrong parameter in request", WRONG_PARAMETER);
        }
    }

    @Override
    public Tag getTag(long id) throws DataNotFoundException {
        log.info("Getting tag with id {}", id);
        Optional<Tag> tag = repository.findById(id);
        return tag.orElseThrow(()
                -> new DataNotFoundException(String.format("Requested resource was not found (id = %d)", id), NOT_FOUND_TAG));
    }

    @Override
    public Tag getTagByName(String name) throws DataNotFoundException {
        log.info("Getting tag with name {}", name);
        Optional<Tag> tag = repository.findTagByName(name);
        return tag.orElseThrow(() ->
                new DataNotFoundException(String.format("Requested resource was not found (name = %s)", name),
                        NOT_FOUND_TAG));
    }

    @Override
    public List<Tag> getTags(Pageable pageable) throws DataNotFoundException {
        log.info("Getting all tags on page = {} with size = {} ", pageable.getPageNumber(), pageable.getPageSize());
        List<Tag> tagList = repository.findAll(pageable).getContent();
        if (!tagList.isEmpty()) {
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
            repository.deleteById(id);
        } catch (DataNotFoundException e) {
            log.error("Exception while deleting tag with id = {}", id, e);
            throw new WrongParameterException("Exception while deleting tag", WRONG_PARAMETER);
        }
    }
}
