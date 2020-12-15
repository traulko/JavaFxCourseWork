package com.traulko.course.builder;

import com.traulko.course.entity.Account;
import com.traulko.course.entity.CreditCard;

import java.time.LocalDate;

public class CreditCardBuilder {
    private Integer creditCardId;
    private long number;
    private LocalDate serviceEndDate;

    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setServiceEndDate(LocalDate serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public CreditCard getCreditCard() {
        return new CreditCard(creditCardId, number, serviceEndDate);
    }
}
