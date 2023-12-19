package com.epam.esm.controllers;

import com.epam.esm.dto.OrderCreatingDto;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.assemblers.OrderModelAssembler;
import com.epam.esm.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for operations with Order model
 */
@RequestMapping(value = "/orders", produces = "application/json", consumes = "application/json")
@Slf4j
@RestController
public class OrderController {
    private final OrderService orderService;
    private final OrderModelAssembler orderModelAssembler;

    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler) {
        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
    }

    /**
     * creating new order from user id and gift certificate id
     *
     * @param orderCreatingDto - dto object (user id, gift certificate id)
     * @return saved order model
     * @throws DataNotFoundException - if user id or gift certificate id were not found
     */
    @PostMapping()
    public OrderModel createOrder(@RequestBody OrderCreatingDto orderCreatingDto)
            throws DataNotFoundException {
        log.info("Creating new order");
        return orderModelAssembler.toModel(orderService
                .createOrder(orderCreatingDto.getUserId(), orderCreatingDto.getGiftCertificateId()));
    }

    /**
     * getting order by id
     *
     * @param id - order id
     * @return order model
     * @throws DataNotFoundException if order was not found
     */
    @GetMapping("/{id}")
    public OrderModel getOrder(@PathVariable("id") Long id) throws DataNotFoundException {
        log.info("Searching for order with id = {}", id);
        return orderModelAssembler.toModel(orderService.findByOrderId(id));
    }
}
