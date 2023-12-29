package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import java.util.List;

public interface UserService {
    User getUserById(Long id) throws DataNotFoundException;
    List<User> getUsers(int page, int size) throws DataNotFoundException;
    List<Order> getUserOrders(Long userId, int page, int size) throws DataNotFoundException;
}
