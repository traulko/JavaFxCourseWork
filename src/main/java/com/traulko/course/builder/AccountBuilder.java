package com.traulko.course.builder;

import com.traulko.course.entity.Account;
import com.traulko.course.entity.CreditCard;
import com.traulko.course.entity.EntityToken;
import com.traulko.course.entity.User;

import java.time.LocalDate;

public class AccountBuilder {
    public enum Status {
        ENABLE, BLOCKED, NOT_CONFIRMED, CLOSED;
    }

    private Integer accountId;
    private LocalDate creationDate;
    private double moneyAmount;
    private Account.Status status;
    private User user;
    private CreditCard creditCard;
    private EntityToken entityToken;

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setStatus(Account.Status status) {
        this.status = status;
    }

    public void setEntityToken(EntityToken entityToken) {
        this.entityToken = entityToken;
    }

    public Account getAccount() {
        return new Account(accountId, creationDate, moneyAmount, status, user, entityToken, creditCard);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
