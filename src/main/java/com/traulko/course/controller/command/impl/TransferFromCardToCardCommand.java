package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.AccountService;
import com.traulko.course.service.CreditCardService;
import com.traulko.course.service.impl.AccountServiceImpl;
import com.traulko.course.service.impl.CreditCardServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransferFromCardToCardCommand implements CustomCommand {
    private static final CreditCardService creditCardService = new CreditCardServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(TransferToAccountCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        boolean result = false;
        String fromCardNumber = requestBatch.getBatchMap().get(RequestParameter.CREDIT_CARD_NUMBER).toString();
        String fromCardDate = requestBatch.getBatchMap().get(RequestParameter.CREDIT_CARD_END_SERVICE_DATE).toString();
        String fromCardCvv = requestBatch.getBatchMap().get(RequestParameter.CREDIT_CARD_CVV).toString();
        String fromCardMoneyAmount = requestBatch.getBatchMap().get(RequestParameter.MONEY_AMOUNT).toString();
        String toCardNumber = requestBatch.getBatchMap().get(RequestParameter.TO_CREDIT_CARD_NUMBER).toString();
        try {
            result = creditCardService.makeTransferFromCardToCard(fromCardNumber, fromCardDate,
                    fromCardCvv, fromCardMoneyAmount, toCardNumber);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while transfer", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.BOOLEAN_RESULT, result);
        return batch;
    }
}
