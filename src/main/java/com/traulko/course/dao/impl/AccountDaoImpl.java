package com.traulko.course.dao.impl;

import com.traulko.course.builder.AccountBuilder;
import com.traulko.course.builder.CreditCardBuilder;
import com.traulko.course.dao.AccountDao;
import com.traulko.course.dao.ColumnName;
import com.traulko.course.dao.connection.ConnectionPool;
import com.traulko.course.entity.Account;
import com.traulko.course.exception.ConnectionDatabaseException;
import com.traulko.course.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {
    private static final AccountDaoImpl INSTANCE = new AccountDaoImpl();

    private static final String ADD_ACCOUNT = "INSERT into account (account_creation_date, account_money_amount," +
            " account_status, account_user_id, account_token_id, account_credit_card_id) values (?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT account_id, account_creation_date, account_money_amount, " +
            "account_status, credit_card_id from account INNER JOIN users on " +
            "user_id = account_user_id INNER JOIN credit_card on credit_card_id = account_credit_card_id";
    private static final String WITH_EMAIL_CONDITION = " where user_email = ?";
    private static final String WITHOUT_CLOSED_CONDITION = " AND account_status != 'CLOSED'";
    private static final String WITH_CREDIT_CARD_NUMBER_CONDITION = " where credit_card_number = ?";
    private static final String WITH_NUMBER_CONDITION = " where account_id = ?";
    private static final String WITH_ID_CONDITION = " where account_id = ?";
    private static final String MAKE_ACCOUNT_ENABLE = "UPDATE account set account_status = \'ENABLE\' where account_id = ?";
    private static final String UNBLOCK_ACCOUNT = "UPDATE account INNER JOIN token ON token_id = account_token_id" +
            " set account_status = \'ENABLE\' where account_id = ? AND token_uuid = ?";
    private static final String BLOCK_ACCOUNT = "UPDATE account set account_status = \'BLOCKED\' where account_id = ?";
    private static final String CLOSE_ACCOUNT = "UPDATE account set account_status = \'CLOSED\' where account_id = ?";
    private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE account set account_money_amount = ? where account_id = ?";

    private AccountDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AccountDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Account account, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(ADD_ACCOUNT)) {
            long creationDateLong = Date.valueOf(account.getCreationDate()).getTime();
            statement.setLong(1, creationDateLong);
            statement.setDouble(2, account.getMoneyAmount());
            statement.setString(3, account.getStatus().toString());
            statement.setInt(4, account.getUser().getUserId());
            statement.setInt(5, account.getToken().getTokenId());
            statement.setInt(6, account.getCreditCard().getCreditCardId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Adding account to table error", e);
        }
    }

    @Override
    public boolean updateBalance(Integer id, double moneyAmount, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE)) {
            statement.setDouble(1, moneyAmount);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Filling up account balance error", e);
        }
    }

    @Override
    public boolean updateBalance(Integer id, double moneyAmount) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE)) {
            statement.setDouble(1, moneyAmount);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Filling up account balance error", e);
        }
    }

    @Override
    public List<Account> findAllByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + WITH_EMAIL_CONDITION)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<Account> accountList = new ArrayList<>();
            while (resultSet.next()) {
                Account account = createAccountFromResultSet(resultSet);
                accountList.add(account);
            }
            return accountList;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding accounts by user email error", e);
        }
    }

    @Override
    public boolean unblock(Integer id, String token) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UNBLOCK_ACCOUNT)) {
            statement.setInt(1, id);
            statement.setString(2, token);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Unblocking account error", e);
        }
    }

    @Override
    public boolean block(Integer id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(BLOCK_ACCOUNT)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Blocking account error", e);
        }
    }

    @Override
    public boolean close(Integer id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CLOSE_ACCOUNT)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Closing account error", e);
        }
    }

    @Override
    public List<Account> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Account> accountList = new ArrayList<>();
            while (resultSet.next()) {
                Account account = createAccountFromResultSet(resultSet);
                accountList.add(account);
            }
            return accountList;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding accounts error", e);
        }
    }

    @Override
    public boolean acceptCreation(Integer id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(MAKE_ACCOUNT_ENABLE)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Unblocking user error", e);
        }
    }

    @Override
    public Optional<Account> findByAccountNumber(Integer accountNumber) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + WITH_NUMBER_CONDITION)) {
            statement.setInt(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            Optional<Account> accountOptional = Optional.empty();
            if (resultSet.next()) {
                Account account = createAccountFromResultSet(resultSet);
                accountOptional = Optional.of(account);
            }
            return accountOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding account by number error", e);
        }
    }

    @Override
    public Optional<Account> findByCreditCardNumber(long creditCardNumber) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL +
                     WITH_CREDIT_CARD_NUMBER_CONDITION)) {
            statement.setLong(1, creditCardNumber);
            ResultSet resultSet = statement.executeQuery();
            Optional<Account> accountOptional = Optional.empty();
            if (resultSet.next()) {
                Account account = createAccountFromResultSet(resultSet);
                accountOptional = Optional.of(account);
            }
            return accountOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding account by credit card number error", e);
        }
    }

    @Override
    public Optional<Account> findById(Integer id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + WITH_ID_CONDITION)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<Account> accountOptional = Optional.empty();
            if (resultSet.next()) {
                Account account = createAccountFromResultSet(resultSet);
                accountOptional = Optional.of(account);
            }
            return accountOptional;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding account by id error", e);
        }
    }

    @Override
    public List<Account> findAllWithoutClosedByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + WITH_EMAIL_CONDITION
                     + WITHOUT_CLOSED_CONDITION)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<Account> accountList = new ArrayList<>();
            while (resultSet.next()) {
                Account account = createAccountFromResultSet(resultSet);
                accountList.add(account);
            }
            return accountList;
        } catch (SQLException | ConnectionDatabaseException e) {
            throw new DaoException("Finding accounts without closed by user email error", e);
        }
    }

    private Account createAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ColumnName.ACCOUNT_ID);
        long creationDateLong = resultSet.getLong(ColumnName.ACCOUNT_CREATION_DATE);
        double moneyAmount = resultSet.getDouble(ColumnName.ACCOUNT_MONEY_AMOUNT);
        LocalDate creationDate = new Date(creationDateLong).toLocalDate();
        Account.Status status = Account.Status.valueOf(resultSet.getString(ColumnName.ACCOUNT_STATUS));
        Integer creditCardId = resultSet.getInt(ColumnName.CREDIT_CARD_ID);
        CreditCardBuilder creditCardBuilder = new CreditCardBuilder();
        creditCardBuilder.setCreditCardId(creditCardId);
        AccountBuilder accountBuilder = new AccountBuilder();
        accountBuilder.setAccountId(id);
        accountBuilder.setCreationDate(creationDate);
        accountBuilder.setStatus(status);
        accountBuilder.setMoneyAmount(moneyAmount);
        accountBuilder.setCreditCard(creditCardBuilder.getCreditCard());
        return accountBuilder.getAccount();
    }
}
