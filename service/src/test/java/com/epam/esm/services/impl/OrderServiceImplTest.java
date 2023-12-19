package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.esm.constants.TestConstants.ACTUAL_ID;
import static com.epam.esm.constants.TestConstants.GiftCertificatesTestConstants.GIFT_CERTIFICATE_1;
import static com.epam.esm.constants.TestConstants.OrderConstants.ORDER_1;
import static com.epam.esm.constants.TestConstants.UserConstants.MOCK_USER_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserService userService;
    @Mock
    private GiftCertificateService giftCertificateService ;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void saveOrder_savedOrder_whenAllCorrect() throws DataNotFoundException {
        when(userService.getUserById(ACTUAL_ID)).thenReturn(MOCK_USER_1);
        when(giftCertificateService.getGiftCertificatesById(ACTUAL_ID)).thenReturn(GIFT_CERTIFICATE_1);
        when(orderRepository.save(any(Order.class))).thenReturn(ORDER_1);

        Order createdOrder = orderService.saveOrder(MOCK_USER_1.getId(), GIFT_CERTIFICATE_1.getId());

        assertEquals(ORDER_1, createdOrder, "Actual order should be equal to expected");
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(giftCertificateService, times(1)).getGiftCertificatesById(ACTUAL_ID);
        verify(giftCertificateService, times(1)).getGiftCertificatesById(ACTUAL_ID);
    }

    @Test
    public void getOrderById_ExpectedOrder_whenOrderExists() throws DataNotFoundException {
        when(orderRepository.findById(ACTUAL_ID)).thenReturn(java.util.Optional.of(ORDER_1));

        Order foundOrder = orderService.getByOrderId(ACTUAL_ID);

        assertEquals(ORDER_1, foundOrder, "Actual order should be equal to expected");
        verify(orderRepository, times(1)).findById(ACTUAL_ID);
    }
}
