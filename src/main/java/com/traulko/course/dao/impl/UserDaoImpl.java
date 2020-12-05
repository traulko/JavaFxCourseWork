package com.traulko.course.dao.impl;

import com.traulko.course.dao.ColumnName;
import com.traulko.course.dao.UserDao;
import com.traulko.course.dao.connection.ConnectionPool;
import com.traulko.course.entity.User;
import com.traulko.course.exception.ConnectionDatabaseException;
import com.traulko.course.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT user_id, user_email, " +
            "user_name, user_surname, user_patronymic, user_role, user_status FROM users" +
            " WHERE user_email = ? AND user_password = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT user_id, user_email, " +
            "user_password, user_name, user_surname, user_patronymic, user_role, user_status" +
            " FROM users WHERE user_email = ?";
    private static final String FIND_USER_BY_TOKEN = "SELECT user_id, user_email, " +
            "user_password, user_name, user_surname, user_patronymic, user_role, user_status" +
            " FROM users INNER JOIN token ON user_token_id = token_id WHERE token_uuid = ?";
    private static final String FIND_ALL = "SELECT user_id, user_email, " +
            "user_password, user_name, user_surname, user_patronymic, user_role, user_status" +
            " FROM users";
    private static final String ADD_USER = "INSERT INTO users (user_email, user_password, user_name," +
            " user_surname, user_patronymic, user_role, user_status, user_token_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String BLOCK_USER = "UPDATE users set user_status = \'BLOCKED\' where user_email = ?";
    private static final String UNBLOCK_USER = "UPDATE users set user_status = \'ENABLE\' where user_email = ?";
    private static final String UPDATE_PASSWORD = "UPDATE users set user_password = ? where user_email = ?";

    private UserDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user, String encryptedPassword, Integer tokenId, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(ADD_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, encryptedPassword);
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getPatronymic());
            statement.setString(6, user.getRole().toString());
            statement.setString(7, user.getStatus().toString());
            statement.setInt(8, tokenId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Adding user to users table error", e);
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            Optional<User> userOptional = Optional.empty();
            if (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                userOptional = Optional.of(user);
            }
            return userOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding user by email and password error", e);
        }
    }

    @Override
    public boolean updatePassword(String email, String encryptedPassword) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD)) {
            statement.setString(1, encryptedPassword);
            statement.setString(2, email);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Error while updating user password", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            Optional<User> userOptional = Optional.empty();
            if (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                userOptional = Optional.of(user);
            }
            return userOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding user by email error", e);
        }
    }

    @Override
    public Optional<User> findByToken(String token) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_TOKEN)) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            Optional<User> userOptional = Optional.empty();
            if (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                userOptional = Optional.of(user);
            }
            return userOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding user by token error", e);
        }
    }

    @Override
    public boolean block(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(BLOCK_USER)) {
            statement.setString(1, email);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Blocking user error", e);
        }
    }

    @Override
    public boolean unblock(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UNBLOCK_USER)) {
            statement.setString(1, email);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Unblocking user error", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                userList.add(user);
            }
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding all users error", e);
        }
        return userList;
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        Integer id = Integer.parseInt(resultSet.getString(ColumnName.USER_ID));
        String email = resultSet.getString(ColumnName.USER_EMAIL);
        User.Role role = User.Role.valueOf(resultSet.getString(ColumnName.USER_ROLE));
        User.Status status = User.Status.valueOf(resultSet.getString(ColumnName.USER_STATUS));
        String name = resultSet.getString(ColumnName.USER_NAME);
        String surname = resultSet.getString(ColumnName.USER_SURNAME);
        String patronymic = resultSet.getString(ColumnName.USER_PATRONYMIC);
        return new User(id, email, name, surname, patronymic, role, status);
    }
}
