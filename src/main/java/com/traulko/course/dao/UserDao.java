package com.traulko.course.dao;

import com.traulko.course.entity.User;
import com.traulko.course.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    boolean add(User user, String encryptedPassword) throws DaoException;

    Optional<User> findByEmailAndPassword(String email, String password) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;
}
