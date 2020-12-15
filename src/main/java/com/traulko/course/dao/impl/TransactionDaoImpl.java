package com.traulko.course.dao.impl;

import com.traulko.course.dao.ColumnName;
import com.traulko.course.dao.TransactionDao;
import com.traulko.course.dao.connection.ConnectionPool;
import com.traulko.course.entity.Account;
import com.traulko.course.entity.EntityTransaction;
import com.traulko.course.exception.ConnectionDatabaseException;
import com.traulko.course.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    private static final TransactionDaoImpl INSTANCE = new TransactionDaoImpl();

    private static final String FIND_TRANSACTION = "SELECT transaction_history_id, from_account_id, to_account_id," +
            " transaction_history_money_amount, transaction_date from transaction_history";
    private static final String BY_ACCOUNT_ID_CONDITION = " where to_account_id = ? OR from_account_id = ?";
    private static final String ADD_TRANSACTION = "INSERT INTO transaction_history (from_account_id, to_account_id," +
            " transaction_history_money_amount, transaction_date) values (?, ?, ?, ?)";

    private TransactionDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TransactionDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<EntityTransaction> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TRANSACTION)) {
            ResultSet resultSet = statement.executeQuery();
            List<EntityTransaction> transactionList = new ArrayList<>();
            while (resultSet.next()) {
                EntityTransaction transaction = createTransactionFromResultSet(resultSet);
                transactionList.add(transaction);
            }
            return transactionList;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding transactions error", e);
        }
    }

    @Override
    public boolean add(EntityTransaction transaction, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(ADD_TRANSACTION)) {
            statement.setInt(1, transaction.getFromAccount().getAccountId());
            statement.setInt(2, transaction.getToAccount().getAccountId());
            statement.setDouble(3, transaction.getMoneyAmount());
            long transferDataLongFormat = Date.valueOf(transaction.getTransactionDate()).getTime();
            statement.setLong(4, transferDataLongFormat);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Addind transaction to table error", e);
        }
    }

    @Override
    public List<EntityTransaction> findByAccountId(Integer id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TRANSACTION +
                     BY_ACCOUNT_ID_CONDITION)) {
            statement.setInt(1, id);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            List<EntityTransaction> transactionList = new ArrayList<>();
            while (resultSet.next()) {
                EntityTransaction transaction = createTransactionFromResultSet(resultSet);
                transactionList.add(transaction);
            }
            return transactionList;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding transaction by account id", e);
        }
    }

    private EntityTransaction createTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        Integer id = Integer.parseInt(resultSet.getString(ColumnName.TRANSACTION_ID));
        Account accountFrom = new Account();
        Integer fromAccountId = resultSet.getInt(ColumnName.TRANSACTION_FROM_ACCOUNT);
        accountFrom.setAccountId(fromAccountId);
        Account accountTo = new Account();
        Integer toAccountId = resultSet.getInt(ColumnName.TRANSACTION_TO_ACCOUNT);
        accountTo.setAccountId(toAccountId);
        double moneyAmount = resultSet.getDouble(ColumnName.TRANSACTION_MONEY_AMOUNT);
        long dateLong = resultSet.getLong(ColumnName.TRANSACTION_DATE);
        LocalDate transactionDate = new Date(dateLong).toLocalDate();
        return new EntityTransaction(id, accountFrom, accountTo, moneyAmount, transactionDate);
    }
}
