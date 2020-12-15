package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.PageManagerHandler;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.*;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.AccountService;
import com.traulko.course.service.CreditCardService;
import com.traulko.course.service.TransactionService;
import com.traulko.course.service.UserService;
import com.traulko.course.service.impl.AccountServiceImpl;
import com.traulko.course.service.impl.CreditCardServiceImpl;
import com.traulko.course.service.impl.TransactionServiceImpl;
import com.traulko.course.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindFullAccountManageInfoCommand implements CustomCommand {
    private static final AccountService accountService = new AccountServiceImpl();
    private static final UserService userService = new UserServiceImpl();
    private static final CreditCardService creditCardService = new CreditCardServiceImpl();
    private static final TransactionService transactionService = new TransactionServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(FindFullAccountManageInfoCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        User user = new User();
        Account account = new Account();
        List<EntityTransaction> transactionList = new ArrayList<>();
        Integer accountId = Integer.valueOf(PageManagerHandler.getInstance().value);
        try {
            Optional<User> optionalUser = userService.findUserByAccountNumber(accountId);
            Optional<Account> optionalAccount = accountService.findAccountByNumber(accountId);
            user = optionalUser.get();
            account = optionalAccount.get();
            Optional<CreditCard> optionalCreditCard = creditCardService
                    .findCreditCardById(account.getCreditCard().getCreditCardId());
            account.setCreditCard(optionalCreditCard.get());
            transactionList = transactionService.findTransactionsByAccountId(accountId);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while finding all account manage info", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.ACCOUNT, account);
        batch.getBatchMap().put(RequestParameter.USER, user);
        batch.getBatchMap().put(RequestParameter.TRANSACTION_LIST, transactionList);
        return batch;
    }
}
