package com.traulko.course.dao;

import com.traulko.course.entity.CreditCard;
import com.traulko.course.exception.DaoException;

import java.sql.Connection;
import java.util.Optional;

public interface CreditCardDao {
    Optional<CreditCard> findByNumber(long number) throws DaoException;

    Optional<CreditCard> findById(Integer id) throws DaoException;

    boolean add(CreditCard creditCard, String encryptedCvv, Connection connection) throws DaoException;

    Optional<CreditCard> findByNumberAndCvv(long number, String cvv) throws DaoException;
}
