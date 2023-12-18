package com.epam.esm.controllers;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Order createOrder(@RequestBody OrderDto orderDto)
            throws DataNotFoundException {
        log.info("Creating new order");
        return orderService.createOrder(orderDto.getUserId(), orderDto.getGiftCertificateId());
    }
}
