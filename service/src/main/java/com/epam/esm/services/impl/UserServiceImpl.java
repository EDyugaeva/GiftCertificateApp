package com.epam.esm.services.impl;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_FOUND_ORDER;
import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_FOUND_USER;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getUserById(Long id) throws DataNotFoundException {
        log.info("Getting user with id {}", id);
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(()
                -> new DataNotFoundException(String.format("Requested resource was not found (id = %d)", id), NOT_FOUND_USER));
    }

    @Override
    public List<User> getUsers(Pageable pageable) throws DataNotFoundException {
        log.info("Getting all users pageable");
        List<User> userList = repository.findAll(pageable).getContent();
        if (!userList.isEmpty()) {
            return userList;
        }
        log.warn("Users were not found");
        throw new DataNotFoundException("Requested resource was not found (users)", NOT_FOUND_USER);
    }

    @Override
    public List<Order> getUserOrders(Long id, PageRequest pageRequest) throws DataNotFoundException {
        log.info("Getting all user orders pageable");
        List<Order> orders = repository.findOrdersByUserId(id, pageRequest).getContent();
        if (!orders.isEmpty()) {
            return orders;
        }
        log.warn("Users were not found");
        throw new DataNotFoundException("Requested resource was not found (user orders)", NOT_FOUND_ORDER);
    }
}
