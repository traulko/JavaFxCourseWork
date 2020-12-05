package com.traulko.course.service;

import com.traulko.course.entity.User;
import com.traulko.course.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean isUserExists(String email, String password);

    Optional<User> findUserByEmail(String email) throws ServiceException;

    boolean updateUserPassword(String email, String password, String passwordRepeat) throws ServiceException;

    Optional<User> findUserByToken(String token) throws ServiceException;

    boolean blockUser(String email) throws ServiceException;

    boolean unblockUser(String email) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;

    boolean addUser(Map<String, String> registrationParameters) throws ServiceException;
}
