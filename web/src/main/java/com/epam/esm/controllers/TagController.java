package com.epam.esm.controllers;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import com.epam.esm.model.TagModel;
import com.epam.esm.model.assemblers.TagModelAssembler;
import com.epam.esm.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import static com.epam.esm.constants.InitValues.PAGE;
import static com.epam.esm.constants.InitValues.SIZE;

/**
 * Class for CRD operations with TAG Model (using HATEOAS)
 */
@RestController
@RequestMapping(value = "/tags", produces = "application/json", consumes = "application/json")
@Slf4j
public class TagController {
    private final TagService tagService;
    private final TagModelAssembler tagMapper;

    public TagController(TagService tagService, TagModelAssembler tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    /**
     * Method for getting tag by id
     *
     * @param id - tag id, should be > 0
     */
    @GetMapping(value = "/{id}")
    public TagModel getTag(@PathVariable("id") Long id) throws DataNotFoundException {
        log.info("Getting tag with id = {} in controller", id);
        return tagMapper.toModel(tagService.getTag(id));
    }

    /**
     * Method for getting all tags
     *
     * @return list of all tags
     */
    @GetMapping(produces = "application/json")
    public CollectionModel<TagModel> getTags(@RequestParam(defaultValue = PAGE, name = "page") int page,
                                             @RequestParam(defaultValue = SIZE, name = "size") int size)
            throws DataNotFoundException {
        log.info("Getting tags");
        PageRequest pageRequest = PageRequest.of(page, size);
        return tagMapper.toCollectionModel(tagService.getTags(pageRequest));
    }

    /**
     * Method for creating new tag
     *
     * @param tag new Tag
     * @return saved tag
     */
    @PostMapping()
    public TagModel saveTag(@RequestBody Tag tag) throws WrongParameterException {
        log.info("Creating new tag with name = {}", tag);
        return tagMapper.toModel(tagService.saveTag(tag));
    }

    /**
     * Method for deleting tag
     *
     * @param id of tag, should be > 0
     */
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Long id) throws WrongParameterException {
        log.info("Deleting tag with id = {} in controller", id);
        tagService.deleteTag(id);
    }

    /**
     * Method for getting most popular tag (with the highest total prive) by user
     *
     * @param userId - user id
     */
    @GetMapping("user/{id}")
    public TagModel getPopularTag(@PathVariable("id") Long userId) throws DataNotFoundException {
        log.info("Getting most popular tag by user {}", userId);
        return tagMapper.toModel(tagService.findMostUsedTagByUser(userId));
    }
}
