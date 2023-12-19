package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.epam.esm.constants.TestConstants.ACTUAL_ID;
import static com.epam.esm.constants.TestConstants.OrderConstants.ORDER_LIST;
import static com.epam.esm.constants.TestConstants.OrderConstants.ORDER_PAGE;
import static com.epam.esm.constants.TestConstants.PAGEABLE;
import static com.epam.esm.constants.TestConstants.UserConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUser_ExpectedUser_whenUserExists() throws DataNotFoundException {
        when(userRepository.findById(ACTUAL_ID)).thenReturn(java.util.Optional.of(MOCK_USER_1));

        User result = userService.getUserById(ACTUAL_ID);

        assertEquals(MOCK_USER_1, result, "Actual user should be equal to expected");
        verify(userRepository, times(1)).findById(ACTUAL_ID);
    }

    @Test
    public void getUsers_ExpectedUserList_whenUsersExist() throws DataNotFoundException {
        when(userRepository.findAll(PAGEABLE)).thenReturn(USER_PAGE);

        List<User> result = userService.getUsers(PAGEABLE);

        assertEquals(USER_LIST, result, "Actual user list should be equal to expected");
        verify(userRepository, times(1)).findAll(PAGEABLE);
    }

    @Test
    public void getUserOrders_ExpectedOrderList_whenOrdersExist() throws DataNotFoundException {
        when(userRepository.findOrdersByUserId(MOCK_USER_1.getId(), PAGEABLE)).thenReturn(ORDER_PAGE);

        List<Order> result = userService.getUserOrders(MOCK_USER_1.getId(), PAGEABLE);

        assertEquals(ORDER_LIST, result, "Actual order list should be equal to expected");
        verify(userRepository, times(1)).findOrdersByUserId(MOCK_USER_1.getId(), PAGEABLE);
    }
}
