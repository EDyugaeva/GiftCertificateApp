package com.epam.esm.controllers;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.UserModel;
import com.epam.esm.model.assemblers.OrderModelAssembler;
import com.epam.esm.model.assemblers.UserModelAssembler;
import com.epam.esm.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for CRUD operations with User model
 */
@RequestMapping(value = "/users", produces = "application/json", consumes = "application/json")
@Slf4j
@RestController
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final OrderModelAssembler orderModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler, OrderModelAssembler orderModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.orderModelAssembler = orderModelAssembler;
    }

    /**
     * Method for getting all users
     *
     * @throws DataNotFoundException if the user table is empty
     */
    @GetMapping()
    public CollectionModel<UserModel> getUsers(@RequestParam(defaultValue = "0", name = "page") int page,
                                               @RequestParam(defaultValue = "10", name = "size") int size)
            throws DataNotFoundException {
        log.info("Getting all users");
        PageRequest pageRequest = PageRequest.of(page, size);
        return userModelAssembler.toCollectionModel(userService.getUsers(pageRequest));
    }

    /**
     * Method for getting all gift certificates (without sorting and ordering)
     *
     * @throws DataNotFoundException if the gift certificate table is empty
     */
    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable("id") Long id) throws DataNotFoundException {
        log.info("Getting user by id = {}", id);
        return userModelAssembler.toModel(userService.getUserById(id));
    }

    /**
     * Getting all orders by user
     * @param userId - user id
     * @param page - page
     * @param size - size of a page
     * @return orders
     * @throws DataNotFoundException if orders were not found
     */
    @GetMapping("/{id}/orders")
    public CollectionModel<OrderModel> getUsersOrders(@PathVariable("id") Long userId,
                                           @RequestParam(defaultValue = "0", name = "page") int page,
                                           @RequestParam(defaultValue = "10", name = "size") int size)
            throws DataNotFoundException {
        log.info("Getting users with userId = {} orders", userId);
        PageRequest pageRequest = PageRequest.of(page, size);
        return orderModelAssembler.toCollectionModel(userService.getUserOrders(userId, pageRequest)) ;
    }
}
