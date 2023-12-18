package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.OrderService;
import com.epam.esm.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final GiftCertificateService giftCertificateService;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, GiftCertificateService giftCertificateService, UserService userService) {
        this.orderRepository = orderRepository;
        this.giftCertificateService = giftCertificateService;
        this.userService = userService;
    }


    @Override
    public Order createOrder(Long userId, Long giftCertificateId) throws DataNotFoundException {
        log.info("Saving new order to user = {} and gc = {}", userId, giftCertificateId);
        Order savingOrder = new Order();
        User user = userService.getUserById(userId);
        GiftCertificate giftCertificate = giftCertificateService.getGiftCertificatesById(giftCertificateId);
        savingOrder.setUser(user);
        savingOrder.setGiftCertificate(giftCertificate);
        savingOrder.setPrice(giftCertificate.getPrice());
        savingOrder.setCreatedTime(LocalDateTime.now());
        return orderRepository.save(savingOrder);
    }
}
