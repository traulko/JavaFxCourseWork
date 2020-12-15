package com.traulko.course.dao;

import com.traulko.course.entity.EntityTransaction;
import com.traulko.course.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface TransactionDao {
    List<EntityTransaction> findAll() throws DaoException;

    boolean add(EntityTransaction transaction, Connection connection) throws DaoException;

    List<EntityTransaction> findByAccountId(Integer id) throws DaoException;
}
