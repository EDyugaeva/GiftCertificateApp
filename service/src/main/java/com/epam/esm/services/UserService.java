package com.epam.esm.services;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.User;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {
    User getUserById(Long id) throws DataNotFoundException;
    List<User> getUsers(Pageable pageable);
}
