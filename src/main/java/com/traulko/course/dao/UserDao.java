package com.traulko.course.dao;

import com.traulko.course.entity.User;
import com.traulko.course.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean add(User user, String encryptedPassword, Integer tokenId, Connection connection) throws DaoException;

    Optional<User> findByEmailAndPassword(String email, String password) throws DaoException;

    boolean updatePassword(String email, String encryptedPassword) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    Optional<User> findByToken(String token) throws DaoException;

    boolean block(String email) throws DaoException;

    boolean unblock(String email) throws DaoException;

    List<User> findAll() throws DaoException;
}
