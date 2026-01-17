package com.example.Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {

    private BankService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankService(new ClientStorage());
    }

    @Test
    void shouldRegisterClientWithPositiveBalance() {
        var result = bankService.registerClient(1, 1000);

        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        assertEquals(1000, result.getNewBalance());
    }

    @Test
    void shouldNotRegisterClientWithNegativeBalance() {
        var result = bankService.registerClient(1, -100);

        assertEquals(TransactionStatus.DECLINED, result.getStatus());
    }

    @Test
    void shouldNotRegisterSameClientTwice() {
        bankService.registerClient(1, 500);

        var result = bankService.registerClient(1, 300);

        assertEquals(TransactionStatus.CLIENT_ALREADY_EXISTS, result.getStatus());
    }

    @Test
    void shouldTransferMoneyWhenEnoughBalance() {
        bankService.registerClient(1, 1000);

        var result = bankService.transfer(1, 400);

        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        assertEquals(600, result.getNewBalance());
    }

    @Test
    void shouldDeclineTransferWhenInsufficientFunds() {
        bankService.registerClient(1, 200);

        var result = bankService.transfer(1, 500);

        assertEquals(TransactionStatus.INSUFFICIENT_FUNDS, result.getStatus());
    }

    @Test
    void shouldDeclineTransferForNonExistingClient() {
        var result = bankService.transfer(99, 100);

        assertEquals(TransactionStatus.CLIENT_NOT_FOUND, result.getStatus());
    }

    @Test
    void shouldDeclineTransferWithZeroAmount() {
        bankService.registerClient(1, 100);

        var result = bankService.transfer(1, 0);

        assertEquals(TransactionStatus.INVALID_AMOUNT, result.getStatus());
    }

    @Test
    void shouldDeclineTransferWithNegativeAmount() {
        bankService.registerClient(1, 100);

        var result = bankService.transfer(1, -50);

        assertEquals(TransactionStatus.INVALID_AMOUNT, result.getStatus());
    }

    @Test
    void shouldDepositMoneySuccessfully() {
        bankService.registerClient(1, 200);

        var result = bankService.deposit(1, 300);

        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        assertEquals(500, result.getNewBalance());
    }

    @Test
    void shouldDeclineDepositForNonExistingClient() {
        var result = bankService.deposit(1, 100);

        assertEquals(TransactionStatus.CLIENT_NOT_FOUND, result.getStatus());
    }

    @Test
    void shouldDeclineDepositWithNegativeAmount() {
        bankService.registerClient(1, 100);

        var result = bankService.deposit(1, -200);

        assertEquals(TransactionStatus.INVALID_AMOUNT, result.getStatus());
    }

    @Test
    void shouldDeclineDepositWithZeroAmount() {
        bankService.registerClient(1, 100);

        var result = bankService.deposit(1, 0);

        assertEquals(TransactionStatus.INVALID_AMOUNT, result.getStatus());
    }

    @Test
    void shouldReturnClientDataWhenClientExists() {
        bankService.registerClient(1, 700);

        var result = bankService.getClientData(1);

        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        assertEquals(700, result.getNewBalance());
    }

    @Test
    void shouldReturnClientNotFoundWhenClientDoesNotExist() {
        var result = bankService.getClientData(10);

        assertEquals(TransactionStatus.CLIENT_NOT_FOUND, result.getStatus());
    }

    @Test
    void balanceShouldBeCorrectAfterMultipleOperations() {
        bankService.registerClient(1, 1000);

        bankService.transfer(1, 200);
        bankService.deposit(1, 300);
        bankService.transfer(1, 100);

        var result = bankService.getClientData(1);

        assertEquals(1000, result.getNewBalance());
    }
}
