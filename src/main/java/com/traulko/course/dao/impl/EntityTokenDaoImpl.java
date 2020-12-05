package com.traulko.course.dao.impl;

import com.traulko.course.dao.EntityTokenDao;
import com.traulko.course.entity.EntityToken;
import com.traulko.course.exception.DaoException;

import java.sql.*;

public class EntityTokenDaoImpl implements EntityTokenDao {
    private static final EntityTokenDaoImpl INSTANCE = new EntityTokenDaoImpl();

    private static final String ADD_TOKEN = "INSERT into token (token_uuid) values (?)";

    private EntityTokenDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EntityTokenDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(EntityToken token, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(ADD_TOKEN, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, token.getTokenUuid());
            boolean result = statement.executeUpdate() > 0;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                token.setTokenId(resultSet.getInt(1));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("Error while adding token", e);
        }
    }
}
