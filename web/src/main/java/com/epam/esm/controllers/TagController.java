package com.epam.esm.controllers;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ApplicationDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.Tag;
import com.epam.esm.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class for CRD operations with TAG model
 */
@RestController
@RequestMapping("/tags")
@Slf4j
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Method for getting tag by id
     *
     * @param id - tag id, should be > 0
     * @return Tag
     */
    @GetMapping("/{id}")
    public Tag getTag(@PathVariable("id") Long id) throws DataNotFoundException {
        log.info("Getting tag with id = {} in controller", id);
        return tagService.getTag(id);
    }

    /**
     * Method for getting all tags
     *
     * @return list of all tags
     */
    @GetMapping()
    public List<Tag> getTags() throws DataNotFoundException {
        log.info("Getting tags");
        return tagService.getTags();
    }

    /**
     * Method for creating new tag
     *
     * @param name of tag
     * @return saved tag
     */
    @PostMapping()
    public Tag saveTag(@RequestBody String name) throws WrongParameterException, ApplicationDatabaseException {
        log.info("Creating new tag with name = {}", name);
        return tagService.saveTag(name);
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
}
