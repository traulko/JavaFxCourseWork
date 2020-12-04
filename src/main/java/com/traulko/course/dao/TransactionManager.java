package com.traulko.course.dao;

import com.traulko.course.dao.connection.ConnectionPool;
import com.traulko.course.dao.impl.EntityTokenDaoImpl;
import com.traulko.course.dao.impl.UserDaoImpl;
import com.traulko.course.entity.EntityToken;
import com.traulko.course.entity.User;
import com.traulko.course.exception.ConnectionDatabaseException;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.TransactionException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final TransactionManager INSTANCE = new TransactionManager();
    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);

    private TransactionManager() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TransactionManager getInstance() {
        return INSTANCE;
    }

    public boolean addUserAndToken(User user, String encryptedPassword, EntityToken token) throws TransactionException {
        Connection connection = null;
        UserDao userDao = UserDaoImpl.getInstance();
        EntityTokenDao entityTokenDao = EntityTokenDaoImpl.getInstance();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean result = entityTokenDao.add(token, connection);
            if (result) {
                result = userDao.add(user, encryptedPassword, connection);
            }
            connection.commit();
            return result;
        } catch (ConnectionDatabaseException | SQLException | DaoException e) {
            rollback(connection);
            throw new TransactionException("Error while adding product and image " + user, e);
        } finally {
            close(connection);
        }
    }

    private void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Rollback error");
        }
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Close connection error");
            }
        }
    }
}
