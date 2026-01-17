package com.example.Bank;

public class TransactionResult {

    private final TransactionStatus status;
    private final double newBalance;

    public TransactionResult(TransactionStatus status, double newBalance) {
        this.status = status;
        this.newBalance = newBalance;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public double getNewBalance() {
        return newBalance;
    }
}
