package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {

    private Double balance;

    public CustomerAccount(Double balance) {
        this.balance = balance;
    }

    public CustomerAccount() {
        this.balance = Double.valueOf(0.0);
    }

    public void add(Double addedAmount) {
        if (addedAmount == null) {
            throw new IllegalArgumentException("amount is null");
        }
        if (addedAmount < 0) {
            throw new IllegalArgumentException("addedAMount is negative, to withdraw money, use Account#withdrawAndReportBalance");
        }
        this.balance += addedAmount;
    }

    public Double getBalance() {
        return this.balance;
    }

    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) 
    		throws IllegalBalanceException {
        if (withdrawnAmount == null) {
            throw new IllegalArgumentException("withdrawnAmount is null");
        }
        if (rule == null) {
            throw new IllegalArgumentException("rule is null");
        }
        if (withdrawnAmount < 0) {
            throw new IllegalArgumentException("withdrawnAmount is negative, to add money, use Account#add");
        }

        double newBalance = this.balance - withdrawnAmount;
        if (rule.withdrawPermitted(newBalance)) {
            this.balance = newBalance;
        } else {
            throw new IllegalBalanceException(newBalance);
        }
        return balance;
    }

}
