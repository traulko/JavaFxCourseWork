package com.traulko.course.service.impl;

import com.traulko.course.dao.TransactionDao;
import com.traulko.course.dao.UserDao;
import com.traulko.course.dao.impl.TransactionDaoImpl;
import com.traulko.course.dao.impl.UserDaoImpl;
import com.traulko.course.entity.Account;
import com.traulko.course.entity.EntityTransaction;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final TransactionDao transactionDao = TransactionDaoImpl.getInstance();

    @Override
    public List<EntityTransaction> findTransactionsByAccountId(Integer id) throws ServiceException {
        List<EntityTransaction> transactionOptional;
        try {
            transactionOptional = transactionDao.findByAccountId(id);
        } catch (DaoException e) {
            throw new ServiceException("Error while finding transactions by account id", e);
        }
        return transactionOptional;
    }

    @Override
    public List<EntityTransaction> findAllTransactions() throws ServiceException {
        List<EntityTransaction> transactionList;
        try {
            transactionList = transactionDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error while finding all transactions", e);
        }
        return transactionList;
    }
}
