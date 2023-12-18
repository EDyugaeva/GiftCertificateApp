package com.epam.esm.controllers;

import com.epam.esm.dto.OrderCreatingDto;
import com.epam.esm.dto.OrderDtoShort;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for CRUD operations with Order model
 */
@RequestMapping(value = "/orders", produces = "application/json", consumes = "application/json")
@Slf4j
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public Order createOrder(@RequestBody OrderCreatingDto orderCreatingDto)
            throws DataNotFoundException {
        log.info("Creating new order");
        return orderService.createOrder(orderCreatingDto.getUserId(), orderCreatingDto.getGiftCertificateId());
    }
    @GetMapping("/{id}")
    public OrderDtoShort GetOrder(@PathVariable("id") Long id)
            throws DataNotFoundException {
        log.info("Searching for order with id = {}", id);
        return orderService.findByOrderId(id);
    }
}
