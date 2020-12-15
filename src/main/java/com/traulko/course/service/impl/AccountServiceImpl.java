package com.traulko.course.service.impl;

import com.traulko.course.builder.AccountBuilder;
import com.traulko.course.builder.CreditCardBuilder;
import com.traulko.course.builder.EntityTokenBuilder;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.dao.AccountDao;
import com.traulko.course.dao.TransactionManager;
import com.traulko.course.dao.impl.AccountDaoImpl;
import com.traulko.course.entity.Account;
import com.traulko.course.entity.CreditCard;
import com.traulko.course.entity.EntityToken;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.exception.TransactionException;
import com.traulko.course.service.AccountService;
import com.traulko.course.service.CreditCardService;
import com.traulko.course.util.CustomCipher;
import com.traulko.course.validator.AccountValidator;
import com.traulko.course.validator.UserValidator;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

public class AccountServiceImpl implements AccountService {
    private static final int DEFAULT_MONEY_AMOUNT = 0;
    private static final int THREE_YEARS = 3;
    private static final String SLASH_SYMBOL = "/";
    private static final int MAX_ACCOUNT_COUNT = 3;
    private final AccountDao accountDao = AccountDaoImpl.getInstance();
    private final TransactionManager transactionManager = TransactionManager.getInstance();

    @Override
    public boolean addAccount(String email, Map<String, String> returnParameters) throws ServiceException {
        boolean result = false;
        try {
            if (!(accountDao.findAllWithoutClosedByEmail(email).size() > MAX_ACCOUNT_COUNT)) {
                AccountBuilder accountBuilder = new AccountBuilder();
                accountBuilder.setCreationDate(LocalDate.now());
                accountBuilder.setMoneyAmount(DEFAULT_MONEY_AMOUNT);
                accountBuilder.setStatus(Account.Status.NOT_CONFIRMED);
                Account account = accountBuilder.getAccount();
                EntityTokenBuilder entityTokenBuilder = new EntityTokenBuilder();
                entityTokenBuilder.setTokenUuid(UUID.randomUUID().toString());
                EntityToken token = entityTokenBuilder.getToken();
                CreditCardService creditCardService = new CreditCardServiceImpl();
                long uniqueCardNumber = creditCardService.generateCreditCardNumber();
                int cvv = creditCardService.generateCreditCardCvv();
                String encryptedCvv = CustomCipher.encrypt(String.valueOf(cvv));
                CreditCardBuilder creditCardBuilder = new CreditCardBuilder();
                creditCardBuilder.setNumber(uniqueCardNumber);
                creditCardBuilder.setServiceEndDate(LocalDate.now().plusYears(THREE_YEARS));
                CreditCard creditCard = creditCardBuilder.getCreditCard();
                result = transactionManager.addAccountAndCreditCardAndToken(account, creditCard,
                        encryptedCvv, token, email);
                if (result) {
                    returnParameters.put(RequestParameter.ACCOUNT_TOKEN, token.getTokenUuid());
                    returnParameters.put(RequestParameter.CREDIT_CARD_CVV, String.valueOf(cvv));
                    returnParameters.put(RequestParameter.CREDIT_CARD_NUMBER, String.valueOf(uniqueCardNumber));
                    int month = creditCard.getServiceEndDate().getMonthValue();
                    int year = creditCard.getServiceEndDate().getYear();
                    String dateStringFormat = month + SLASH_SYMBOL + year;
                    returnParameters.put(RequestParameter.CREDIT_CARD_END_SERVICE_DATE, dateStringFormat);
                }
            }
        } catch (DaoException | NoSuchAlgorithmException | TransactionException e) {
            throw new ServiceException("Error while creating account", e);
        }
        return result;
    }

    @Override
    public boolean acceptCreationAccount(Integer id) throws ServiceException {
        boolean result = false;
        try {
            result = accountDao.acceptCreation(id);
        } catch (DaoException e) {
            throw new ServiceException("Error while accepting creation account", e);
        }
        return result;
    }

    @Override
    public List<Account> findAllAccounts() throws ServiceException {
        List<Account> accountList = new ArrayList<>();
        try {
            accountList = accountDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error while finding all accounts", e);
        }
        return accountList;
    }

    @Override
    public Optional<Account> findAccountByNumber(Integer accountNumber) throws ServiceException {
        Optional<Account> optionalAccount = Optional.empty();
        try {
            optionalAccount = accountDao.findByAccountNumber(accountNumber);
        } catch (DaoException e) {
            throw new ServiceException("Error while finding account by number", e);
        }
        return optionalAccount;
    }

    @Override
    public boolean blockAccount(Integer id) throws ServiceException {
        boolean result;
        try {
            result = accountDao.block(id);
        } catch (DaoException e) {
            throw new ServiceException("Error while blocking account", e);
        }
        return result;
    }

    @Override
    public boolean closeAccount(Integer id) throws ServiceException {
        boolean result;
        try {
            result = accountDao.close(id);
        } catch (DaoException e) {
            throw new ServiceException("Error while closing account", e);
        }
        return result;
    }

    @Override
    public boolean fillAccountBalance(Integer id, String moneyAmount) throws ServiceException {
        boolean result = false;
        try {
            if (AccountValidator.isMoneyAmountValid(moneyAmount)) {
                double moneyAmountParsed = Double.parseDouble(moneyAmount);
                Optional<Account> optionalAccount = accountDao.findById(id);
                double oldBalance = optionalAccount.get().getMoneyAmount();
                double newBalance = moneyAmountParsed + oldBalance;
                result = accountDao.updateBalance(id, newBalance);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while filling up balance", e);
        }
        return result;
    }

    @Override
    public boolean makeTransferToAccount(Integer id, String toAccountId, String moneyAmount) throws ServiceException {
        boolean result = false;
        try {
            if (AccountValidator.isMoneyAmountValid(moneyAmount) &&
                    AccountValidator.isIdValueValid(toAccountId)) {
                double moneyAmountParsed = Double.parseDouble(moneyAmount);
                Integer toAccountIdParsed = Integer.parseInt(toAccountId);
                result = transactionManager.makeTransfer(id, toAccountIdParsed, moneyAmountParsed);
            }
        } catch (TransactionException e) {
            throw new ServiceException("Error while transfer", e);
        }
        return result;
    }

    @Override
    public boolean unblockAccount(Integer id, String token) throws ServiceException {
        boolean result;
        try {
            String encryptedToken = CustomCipher.encrypt(token);
            result = accountDao.unblock(id, encryptedToken);
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException("Error while unblocking account", e);
        }
        return result;
    }

    @Override
    public List<Account> findAccountsByEmail(String email) throws ServiceException {
        List<Account> accountList = new ArrayList<>();
        try {
            if (UserValidator.isEmailValid(email)) {
                accountList = accountDao.findAllByEmail(email);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while finding all accounts by email", e);
        }
        return accountList;
    }
}
