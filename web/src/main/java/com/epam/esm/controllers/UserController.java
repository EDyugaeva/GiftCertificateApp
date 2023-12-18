package com.epam.esm.controllers;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for CRUD operations with User model
 */
@RequestMapping(value = "/users", produces = "application/json", consumes = "application/json")
@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method for getting all gift certificates (without sorting and ordering)
     *
     * @throws DataNotFoundException if the gift certificate table is empty
     */
    @GetMapping()
    public List<User> getAllGiftCertificates(@RequestParam(defaultValue = "0", name = "page") int page,
                                             @RequestParam(defaultValue = "10", name = "size") int size)
            throws DataNotFoundException {
        log.info("Getting all users");
        PageRequest pageRequest = PageRequest.of(page, size);
        return userService.getUsers(pageRequest);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getUsersOrders(@PathVariable("id") Long userId,

                                      @RequestParam(defaultValue = "0", name = "page")
                                      int page,
                                      @RequestParam(defaultValue = "10", name = "size")
                                      int size)
            throws DataNotFoundException {
        log.info("Getting users with userId = {} orders", userId);
        PageRequest pageRequest = PageRequest.of(page, size);
        return userService.getUserOrders(userId, pageRequest);
    }
}
