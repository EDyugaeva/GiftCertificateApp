package com.epam.esm.controllers;

import com.epam.esm.model.Tag;
import com.epam.esm.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/hello")
    public String printHelloWorld() {

        return "helloWorld";
    }

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable("id") Long id) {
        System.out.println("working");
        return tagService.getTag(id);
    }

}
