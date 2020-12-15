package com.traulko.course.service;

import com.traulko.course.entity.Account;
import com.traulko.course.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountService {
    boolean addAccount(String email, Map<String, String> returnParameters) throws ServiceException;

    boolean acceptCreationAccount(Integer id) throws ServiceException;

    List<Account> findAllAccounts() throws ServiceException;

    Optional<Account> findAccountByNumber(Integer accountNumber) throws ServiceException;

    boolean blockAccount(Integer id) throws ServiceException;

    boolean closeAccount(Integer id) throws ServiceException;

    boolean fillAccountBalance(Integer id, String moneyAmount) throws ServiceException;

    boolean makeTransferToAccount(Integer id, String toAccountId, String moneyAmount) throws ServiceException;

    boolean unblockAccount(Integer id, String token) throws ServiceException;

    List<Account> findAccountsByEmail(String email) throws ServiceException;
}
