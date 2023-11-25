package com.epam.esm.controllers;

import com.epam.esm.model.Tag;
import com.epam.esm.services.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class for CRD operations with TAG model
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    private Logger logger = LoggerFactory.getLogger(TagController.class);

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
    public Tag getTag(@PathVariable("id") Long id) {
        logger.info("Getting tag with id = {} in controller", id);
        return tagService.getTag(id);
    }

    /**
     * Method for getting all tags
     *
     * @return list of all tags
     */
    @GetMapping()
    public List<Tag> getTags() {
        logger.info("Getting tags");
        return tagService.getTags();
    }

    /**
     * Method for creating new tag
     *
     * @param name of tag
     * @return saved tag
     */
    @PostMapping()
    public Tag saveTag(@RequestBody String name) {
        logger.info("Creating new tag with name = {}", name);
        return tagService.saveTag(name);
    }

    /**
     * Method for deleting tag
     *
     * @param id of tag, should be > 0
     */
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Long id) {
        logger.info("Deleting tag with id = {} in controller", id);
        tagService.deleteTag(id);
    }
}
