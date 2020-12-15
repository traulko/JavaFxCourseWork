package com.traulko.course.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Account implements Serializable {
    public enum Status {
        ENABLE, BLOCKED, NOT_CONFIRMED, CLOSED;
    }

    private Integer accountId;
    private LocalDate creationDate;
    private double moneyAmount;
    private Status status;
    private User user;
    private EntityToken token;
    private CreditCard creditCard;

    public Account() {}

    public Account(Integer accountId, LocalDate creationDate, double moneyAmount,
                   Status status, User user, EntityToken token, CreditCard creditCard) {
        this.accountId = accountId;
        this.creationDate = creationDate;
        this.moneyAmount = moneyAmount;
        this.status = status;
        this.user = user;
        this.token = token;
        this.creditCard = creditCard;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EntityToken getToken() {
        return token;
    }

    public void setToken(EntityToken token) {
        this.token = token;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
