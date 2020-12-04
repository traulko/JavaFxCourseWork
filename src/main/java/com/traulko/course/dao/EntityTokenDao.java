package com.traulko.course.dao;

import com.traulko.course.entity.EntityToken;
import com.traulko.course.exception.DaoException;

import java.sql.Connection;

public interface EntityTokenDao {
    boolean add(EntityToken token, Connection connection) throws DaoException;
}
