package com.traulko.course.service;

import com.traulko.course.entity.User;
import com.traulko.course.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    boolean isUserExists(String email, String password);

    Optional<User> findUserByEmail(String email) throws ServiceException;
}
