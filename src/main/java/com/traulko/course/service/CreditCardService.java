package com.traulko.course.service;

import com.traulko.course.entity.CreditCard;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.ServiceException;

import java.util.Optional;

public interface CreditCardService {
    long generateCreditCardNumber() throws DaoException;

    boolean makeTransferFromCardToCard(String fromCardNumber, String fromCardDate,
                                       String fromCardCvv, String fromCardMoneyAmount, String toCardNumber) throws ServiceException;

    Optional<CreditCard> findCreditCardById(Integer id) throws ServiceException;

    int generateCreditCardCvv();
}
