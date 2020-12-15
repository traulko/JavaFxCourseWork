package com.traulko.course.dao;

import com.traulko.course.entity.Account;
import com.traulko.course.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface AccountDao {
    boolean add(Account account, Connection connection) throws DaoException;

    boolean updateBalance(Integer id, double moneyAmount, Connection connection) throws DaoException;

    boolean updateBalance(Integer id, double moneyAmount) throws DaoException;

    List<Account> findAllByEmail(String email) throws DaoException;

    boolean unblock(Integer id, String token) throws DaoException;

    boolean block(Integer id) throws DaoException;

    boolean close(Integer id) throws DaoException;

    List<Account> findAll() throws DaoException;

    boolean acceptCreation(Integer id) throws DaoException;

    Optional<Account> findByAccountNumber(Integer accountNumber) throws DaoException;

    Optional<Account> findByCreditCardNumber(long creditCardNumber) throws DaoException;

    Optional<Account> findById(Integer id) throws DaoException;

    List<Account> findAllWithoutClosedByEmail(String email) throws DaoException;
}
