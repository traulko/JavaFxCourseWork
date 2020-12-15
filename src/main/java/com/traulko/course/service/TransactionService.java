package com.traulko.course.service;

import com.traulko.course.entity.EntityTransaction;
import com.traulko.course.exception.ServiceException;

import java.util.List;

public interface TransactionService {
    List<EntityTransaction> findTransactionsByAccountId(Integer id) throws ServiceException;

    List<EntityTransaction> findAllTransactions() throws ServiceException;
}
