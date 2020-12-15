package com.traulko.course.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class CreditCard implements Serializable {
    private Integer creditCardId;
    private long number;
    private LocalDate serviceEndDate;

    public CreditCard(Integer creditCardId, long number, LocalDate serviceEndDate) {
        this.creditCardId = creditCardId;
        this.number = number;
        this.serviceEndDate = serviceEndDate;
    }

    public Integer getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public LocalDate getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(LocalDate serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }
}
