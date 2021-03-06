package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.AccountService;
import com.traulko.course.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockAccountCommand implements CustomCommand {
    private static final AccountService accountService = new AccountServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(BlockAccountCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        boolean result = false;
        Integer accountId = (int) requestBatch.getBatchMap().get(RequestParameter.ACCOUNT_ID);
        try {
            result = accountService.blockAccount(accountId);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while blocking account", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.BOOLEAN_RESULT, result);
        return batch;
    }
}
