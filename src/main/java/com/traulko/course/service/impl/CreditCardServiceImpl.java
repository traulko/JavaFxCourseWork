package com.traulko.course.service.impl;

import com.traulko.course.dao.CreditCardDao;
import com.traulko.course.dao.TransactionManager;
import com.traulko.course.dao.impl.CreditCardDaoImpl;
import com.traulko.course.entity.CreditCard;
import com.traulko.course.entity.User;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.exception.TransactionException;
import com.traulko.course.service.CreditCardService;
import com.traulko.course.validator.CreditCardValidator;
import com.traulko.course.validator.UserValidator;

import java.util.Optional;
import java.util.Random;
import java.util.function.LongToDoubleFunction;

public class CreditCardServiceImpl implements CreditCardService {
    private final TransactionManager transactionManager = TransactionManager.getInstance();
    private final CreditCardDao creditCardDao = CreditCardDaoImpl.getInstance();

    @Override
    public long generateCreditCardNumber() throws DaoException {
        Random random = new Random();
        long cardNumber = 0;
        boolean isCorrectGenerated = false;
        while (!isCorrectGenerated) {
            cardNumber = 1 + random.nextInt(9);
            for (int i = 0; i < 15; i++) {
                cardNumber *= 10L;
                cardNumber += random.nextInt(10);
            }
            Optional<CreditCard> optionalCreditCard = creditCardDao.findByNumber(cardNumber);
            if (optionalCreditCard.isEmpty()) {
                isCorrectGenerated = true;
            }
        }
        return cardNumber;
    }

    @Override
    public boolean makeTransferFromCardToCard(String fromCardNumber, String fromCardDate,
                                              String fromCardCvv, String fromCardMoneyAmount, String toCardNumber) throws ServiceException {
        boolean result = false;
        if (CreditCardValidator.isCreditCardNumberValid(fromCardNumber) &&
                CreditCardValidator.isCreditCardNumberValid(toCardNumber) &&
                CreditCardValidator.isMoneyAmountValid(fromCardMoneyAmount) &&
                CreditCardValidator.isCreditCardDateValid(fromCardDate) &&
                CreditCardValidator.isCreditCardCvvValid(fromCardCvv)) {
            try {
                long fromCardNumberParsed = Long.parseLong(fromCardNumber);
                int fromCardCvvParsed = Integer.parseInt(fromCardCvv);
                double fromCardMoneyAmountParsed = Double.parseDouble(fromCardMoneyAmount);
                long toCardNumberParsed = Long.parseLong(toCardNumber);
                result = transactionManager.makeTransfer(fromCardNumberParsed, fromCardDate,
                        fromCardCvvParsed, fromCardMoneyAmountParsed, toCardNumberParsed);
            } catch (TransactionException e) {
                throw new ServiceException("Error while transfering from card to card", e);
            }
        }
        return result;
    }

    @Override
    public Optional<CreditCard> findCreditCardById(Integer id) throws ServiceException {
        Optional<CreditCard> creditCardOptional;
        try {
            creditCardOptional = creditCardDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error while getting credit card by id", e);
        }
        return creditCardOptional;
    }


    @Override
    public int generateCreditCardCvv() {
        Random random = new Random();
        int cvv = 0;
        for (int i = 0; i < 3; i++) {
            cvv *= 10;
            cvv += random.nextInt(10);
        }
        return cvv;
    }
}
