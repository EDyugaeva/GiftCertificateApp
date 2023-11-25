package com.epam.esm.controllers;

import com.epam.esm.exceptions.CustomException;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.services.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    Logger logger = LoggerFactory.getLogger(TagController.class);

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/hello")
    public String printHelloWorld() {
        logger.info("Hello from tag controller");
        return "helloWorld";
    }

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable("id") Long id) {
        logger.info("Getting tag with id = {} in controller", id);
        try {
            System.out.println("in exception");
            tagService.getTag(id);
        } catch (Exception e) {
            System.out.println("in handling");
            throw new CustomException("sdfsd");
        }
        return tagService.getTag(id);
    }

    @GetMapping()
    public List<Tag> getTags() {
        logger.info("Getting tags");
        return tagService.getTags();
    }

    @GetMapping("/ex")
    public String getException() {
        throw new DataNotFoundException("exception", "exception");
    }

    @PostMapping()
    public Tag getTag(@RequestBody String name) {
        System.out.println(name);
        logger.info("Creating new tag with name = ", name);
        return tagService.saveTag(name);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Long id) {
        logger.info("Deleting tag with id = {} in controller", id);
        tagService.deleteTag(id);
    }
}
