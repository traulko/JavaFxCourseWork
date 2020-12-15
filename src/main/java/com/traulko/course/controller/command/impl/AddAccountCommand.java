package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.UserSession;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.AccountService;
import com.traulko.course.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddAccountCommand implements CustomCommand {
    private static final AccountService accountService = new AccountServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(AddAccountCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        boolean result = false;
        Batch batch = new Batch();
        Map<String, String> returnParameters = new HashMap<>();
        String email = UserSession.getInstance().email;
        try {
            result = accountService.addAccount(email, returnParameters);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while adding account", e);
        }
        batch.getBatchMap().put(RequestParameter.BOOLEAN_RESULT, result);
        if (result) {
            batch.getBatchMap().put(RequestParameter.ACCOUNT_TOKEN,
                    returnParameters.get(RequestParameter.ACCOUNT_TOKEN));
            batch.getBatchMap().put(RequestParameter.CREDIT_CARD_CVV,
                    returnParameters.get(RequestParameter.CREDIT_CARD_CVV));
            batch.getBatchMap().put(RequestParameter.CREDIT_CARD_NUMBER,
                    returnParameters.get(RequestParameter.CREDIT_CARD_NUMBER));
            batch.getBatchMap().put(RequestParameter.CREDIT_CARD_END_SERVICE_DATE,
                    returnParameters.get(RequestParameter.CREDIT_CARD_END_SERVICE_DATE));
        }
        return batch;
    }
}
