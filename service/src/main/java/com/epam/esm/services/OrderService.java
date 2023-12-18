package com.epam.esm.services;

import com.epam.esm.dto.OrderDtoShort;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Order;

public interface OrderService {
    Order createOrder(Long userId, Long giftCertificateId) throws DataNotFoundException;
    OrderDtoShort findByOrderId(Long orderId) throws DataNotFoundException;
}
