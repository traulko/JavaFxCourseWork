package com.traulko.course.dao;

import com.traulko.course.builder.CreditCardBuilder;
import com.traulko.course.builder.TransactionBuilder;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.dao.connection.ConnectionPool;
import com.traulko.course.dao.impl.*;
import com.traulko.course.entity.Account;
import com.traulko.course.entity.CreditCard;
import com.traulko.course.entity.EntityToken;
import com.traulko.course.entity.User;
import com.traulko.course.exception.ConnectionDatabaseException;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.TransactionException;
import com.traulko.course.util.CustomCipher;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

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
                result = userDao.add(user, encryptedPassword, token.getTokenId(), connection);
            }
            connection.commit();
            return result;
        } catch (ConnectionDatabaseException | SQLException | DaoException e) {
            rollback(connection);
            throw new TransactionException("Error while adding user and token " + user, e);
        } finally {
            close(connection);
        }
    }

    public boolean makeTransfer(Integer fromAccountId, Integer toAccountId, double moneyAmount) throws TransactionException {
        Connection connection = null;
        boolean result = false;
        AccountDao accountDao = AccountDaoImpl.getInstance();
        TransactionDao transactionDao = TransactionDaoImpl.getInstance();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            Optional<Account> optionalAccount = accountDao.findById(fromAccountId);
            Account fromAccount = optionalAccount.get();
            if (fromAccount.getMoneyAmount() >= moneyAmount) {
                double newBalance = fromAccount.getMoneyAmount() - moneyAmount;
                result = accountDao.updateBalance(fromAccountId, newBalance, connection);
                if (result) {
                    optionalAccount = accountDao.findById(toAccountId);
                    Account toAccount = optionalAccount.get();
                    if (toAccount.getStatus() == Account.Status.ENABLE) {
                        newBalance = toAccount.getMoneyAmount() + moneyAmount;
                        result = accountDao.updateBalance(toAccountId, newBalance, connection);
                        if (result) {
                            TransactionBuilder transactionBuilder = new TransactionBuilder();
                            transactionBuilder.setFromAccount(fromAccount);
                            transactionBuilder.setToAccount(toAccount);
                            transactionBuilder.setMoneyAmount(moneyAmount);
                            transactionBuilder.setTransactionDate(LocalDate.now());
                            result = transactionDao.add(transactionBuilder.getTransaction(), connection);
                        }
                    } else {
                        result = false;
                    }
                }
            }
            if (result) {
                connection.commit();
            }
            return result;
        } catch (ConnectionDatabaseException | SQLException | DaoException e) {
            rollback(connection);
            throw new TransactionException("Error while transfer " + fromAccountId + " to " + toAccountId, e);
        } finally {
            close(connection);
        }
    }

    public boolean makeTransfer(long fromCardNumber, String fromCardDate, int fromCardCvv,
                                double moneyAmount, long toCardNumber) throws TransactionException {
        Connection connection = null;
        boolean result = false;
        AccountDao accountDao = AccountDaoImpl.getInstance();
        CreditCardDao creditCardDao = CreditCardDaoImpl.getInstance();
        TransactionDao transactionDao = TransactionDaoImpl.getInstance();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            Optional<CreditCard> creditCardOptional = creditCardDao.findByNumber(fromCardNumber);
            if (creditCardOptional.isPresent()) {
                CreditCard creditCard = creditCardOptional.get();
                int month = creditCard.getServiceEndDate().getMonthValue();
                int year = creditCard.getServiceEndDate().getYear();
                String dateStringFormat = month + PromptMessages.SLASH + year;
                if (dateStringFormat.equals(fromCardDate)) {
                    String encryptedCvv = CustomCipher.encrypt(String.valueOf(fromCardCvv));
                    Optional<CreditCard> optionalFromCreditCard = creditCardDao
                            .findByNumberAndCvv(fromCardNumber, encryptedCvv);
                    if (optionalFromCreditCard.isPresent()) {
                        Optional<CreditCard> optionalToCreditCard = creditCardDao.findByNumber(toCardNumber);
                        if (optionalToCreditCard.isPresent()) {
                            Optional<Account> optionalFromAccount = accountDao.findByCreditCardNumber(fromCardNumber);
                            if (optionalFromAccount.isPresent()) {
                                Account fromAccount = optionalFromAccount.get();
                                if (fromAccount.getMoneyAmount() >= moneyAmount) {
                                    double newBalance = fromAccount.getMoneyAmount() - moneyAmount;
                                    result = accountDao.updateBalance(fromAccount.getAccountId(), newBalance, connection);
                                    if (result) {
                                        Optional<Account> optionalToAccount = accountDao.findByCreditCardNumber(toCardNumber);
                                        if (optionalToAccount.isPresent()) {
                                            Account toAccount = optionalToAccount.get();
                                            newBalance = toAccount.getMoneyAmount() + moneyAmount;
                                            result = accountDao.updateBalance(toAccount.getAccountId(), newBalance, connection);
                                            if (result) {
                                                TransactionBuilder transactionBuilder = new TransactionBuilder();
                                                transactionBuilder.setFromAccount(fromAccount);
                                                transactionBuilder.setToAccount(toAccount);
                                                transactionBuilder.setMoneyAmount(moneyAmount);
                                                transactionBuilder.setTransactionDate(LocalDate.now());
                                                result = transactionDao.add(transactionBuilder.getTransaction(), connection);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            connection.commit();
            return result;
        } catch (ConnectionDatabaseException | SQLException | DaoException | NoSuchAlgorithmException e) {
            rollback(connection);
            throw new TransactionException("Error while transfer " + fromCardNumber + " to " + fromCardNumber, e);
        } finally {
            close(connection);
        }
    }

    public boolean addAccountAndCreditCardAndToken(Account account, CreditCard creditCard, String encryptedCvv,
                                                   EntityToken token, String email) throws TransactionException {
        Connection connection = null;
        UserDao userDao = UserDaoImpl.getInstance();
        EntityTokenDao entityTokenDao = EntityTokenDaoImpl.getInstance();
        AccountDao accountDao = AccountDaoImpl.getInstance();
        CreditCardDao creditCardDao = CreditCardDaoImpl.getInstance();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean result = entityTokenDao.add(token, connection);
            if (result) {
                result = creditCardDao.add(creditCard, encryptedCvv, connection);
                if (result) {
                    Optional<User> optionalUser = userDao.findBy(email);
                    if (optionalUser.isPresent()) {
                        account.setUser(optionalUser.get());
                        account.setToken(token);
                        account.setCreditCard(creditCard);
                        result = accountDao.add(account, connection);
                    }
                }
            }
            connection.commit();
            return result;
        } catch (ConnectionDatabaseException | SQLException | DaoException e) {
            rollback(connection);
            throw new TransactionException("Error while adding account " + account, e);
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
