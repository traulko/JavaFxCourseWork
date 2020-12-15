package com.traulko.course.dao.impl;

import com.traulko.course.dao.ColumnName;
import com.traulko.course.dao.CreditCardDao;
import com.traulko.course.dao.connection.ConnectionPool;
import com.traulko.course.entity.CreditCard;
import com.traulko.course.entity.User;
import com.traulko.course.exception.ConnectionDatabaseException;
import com.traulko.course.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class CreditCardDaoImpl implements CreditCardDao {
    private static final CreditCardDaoImpl INSTANCE = new CreditCardDaoImpl();

    private static final String FIND_ALL = "SELECT credit_card_id, credit_card_number," +
            " credit_card_service_end from credit_card";
    private static final String BY_ID_CONDITION = " where credit_card_id = ?";
    private static final String BY_NUMBER_CONDITION = " where credit_card_number = ?";
    private static final String AND_BY_CVV_CONDITION = " AND credit_card_cvv = ?";
    private static final String ADD_CREDIT_CARD = "INSERT into credit_card (credit_card_number, credit_card_service_end, " +
            "credit_card_cvv) values (?, ?, ?)";

    private CreditCardDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CreditCardDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<CreditCard> findByNumber(long number) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + BY_NUMBER_CONDITION)) {
            statement.setLong(1, number);
            ResultSet resultSet = statement.executeQuery();
            Optional<CreditCard> creditCardOptional = Optional.empty();
            if (resultSet.next()) {
                CreditCard creditCard = createCreditCardFromResultSet(resultSet);
                creditCardOptional = Optional.of(creditCard);
            }
            return creditCardOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding credit card by number error", e);
        }
    }

    @Override
    public Optional<CreditCard> findById(Integer id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + BY_ID_CONDITION)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<CreditCard> creditCardOptional = Optional.empty();
            if (resultSet.next()) {
                CreditCard creditCard = createCreditCardFromResultSet(resultSet);
                creditCardOptional = Optional.of(creditCard);
            }
            return creditCardOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding credit card by id error", e);
        }
    }

    @Override
    public boolean add(CreditCard creditCard, String encryptedCvv, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection
                .prepareStatement(ADD_CREDIT_CARD, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, creditCard.getNumber());
            long serviceEndLong = Date.valueOf(creditCard.getServiceEndDate()).getTime();
            statement.setLong(2, serviceEndLong);
            statement.setString(3, encryptedCvv);
            boolean result = statement.executeUpdate() > 0;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                creditCard.setCreditCardId(resultSet.getInt(1));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("Error while adding credit card", e);
        }
    }

    @Override
    public Optional<CreditCard> findByNumberAndCvv(long number, String cvv) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL +
                     BY_NUMBER_CONDITION + AND_BY_CVV_CONDITION)) {
            statement.setLong(1, number);
            statement.setString(2, cvv);
            ResultSet resultSet = statement.executeQuery();
            Optional<CreditCard> creditCardOptional = Optional.empty();
            if (resultSet.next()) {
                CreditCard creditCard = createCreditCardFromResultSet(resultSet);
                creditCardOptional = Optional.of(creditCard);
            }
            return creditCardOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding credit card by number and cvv error", e);
        }
    }

    private CreditCard createCreditCardFromResultSet(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ColumnName.CREDIT_CARD_ID);
        long number = resultSet.getLong(ColumnName.CREDIT_CARD_NUMBER);
        long serviceEndLong = resultSet.getLong(ColumnName.CREDIT_CARD_SERVICE_END);
        LocalDate serviceEndDate = new Date(serviceEndLong).toLocalDate();
        return new CreditCard(id, number, serviceEndDate);
    }
}
