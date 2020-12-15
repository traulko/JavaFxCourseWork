package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.EntityTransaction;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.TransactionService;
import com.traulko.course.service.impl.TransactionServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FindAllTransactionsCommand implements CustomCommand {
    private static final TransactionService transactionService = new TransactionServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(FindAllTransactionsCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        List<EntityTransaction> transactionList = new ArrayList<>();
        try {
            transactionList = transactionService.findAllTransactions();
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while finding all accounts", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.TRANSACTION_LIST, transactionList);
        return batch;
    }
}
