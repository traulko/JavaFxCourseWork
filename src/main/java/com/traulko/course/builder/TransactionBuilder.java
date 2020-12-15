package com.traulko.course.builder;

import com.traulko.course.entity.Account;
import com.traulko.course.entity.EntityTransaction;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;

public class TransactionBuilder {
    private Integer transactionId;
    private Account fromAccount;
    private Account toAccount;
    private double moneyAmount;
    private LocalDate transactionDate;

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public EntityTransaction getTransaction() {
        return new EntityTransaction(transactionId, fromAccount, toAccount, moneyAmount, transactionDate);
    }
}
