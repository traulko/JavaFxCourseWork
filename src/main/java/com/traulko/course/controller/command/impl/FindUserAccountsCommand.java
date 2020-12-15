package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Account;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.User;
import com.traulko.course.entity.UserSession;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.AccountService;
import com.traulko.course.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FindUserAccountsCommand implements CustomCommand {
    private static final AccountService accountService = new AccountServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(FindUserAccountsCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        List<Account> accountList = new ArrayList<>();
        String email = UserSession.getInstance().email;
        try {
            accountList = accountService.findAccountsByEmail(email);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while finding all user accounts", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.ACCOUNT_LIST, accountList);
        return batch;
    }
}
