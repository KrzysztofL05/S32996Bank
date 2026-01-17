package com.example.Bank;

public enum TransactionStatus {
    ACCEPTED,
    DECLINED,

    CLIENT_NOT_FOUND,
    CLIENT_ALREADY_EXISTS,

    INSUFFICIENT_FUNDS,
    INVALID_AMOUNT
}
