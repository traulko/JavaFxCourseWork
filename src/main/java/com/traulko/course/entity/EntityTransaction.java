package com.traulko.course.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class EntityTransaction implements Serializable {
    private Integer transactionId;
    private Account fromAccount;
    private Account toAccount;
    private double moneyAmount;
    private LocalDate transactionDate;

    public EntityTransaction () {}

    public EntityTransaction(Integer transactionId, Account fromAccount,
                             Account toAccount, double moneyAmount, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.moneyAmount = moneyAmount;
        this.transactionDate = transactionDate;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
