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
        // Given
        int clientId = 1;
        int initialBalance = 1000;

        // When
        var result = bankService.registerClient(clientId, initialBalance);

        // Then
        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        assertEquals(initialBalance, result.getNewBalance());
    }

    @Test
    void shouldNotRegisterClientWithNegativeBalance() {
        // Given
        int clientId = 1;
        int initialBalance = -100;

        // When
        var result = bankService.registerClient(clientId, initialBalance);

        // Then
        assertEquals(TransactionStatus.DECLINED, result.getStatus());
    }

    @Test
    void shouldNotRegisterSameClientTwice() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 500);

        // When
        var result = bankService.registerClient(clientId, 300);

        // Then
        assertEquals(TransactionStatus.CLIENT_ALREADY_EXISTS, result.getStatus());
    }

    @Test
    void shouldTransferMoneyWhenEnoughBalance() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 1000);
        int transferAmount = 400;

        // When
        var result = bankService.transfer(clientId, transferAmount);

        // Then
        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        assertEquals(600, result.getNewBalance());
    }

    @Test
    void shouldDeclineTransferWhenInsufficientFunds() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 200);
        int transferAmount = 500;

        // When
        var result = bankService.transfer(clientId, transferAmount);

        // Then
        assertEquals(TransactionStatus.INSUFFICIENT_FUNDS, result.getStatus());
    }

    @Test
    void shouldDeclineTransferForNonExistingClient() {
        // Given
        int clientId = 99;
        int transferAmount = 100;

        // When
        var result = bankService.transfer(clientId, transferAmount);

        // Then
        assertEquals(TransactionStatus.CLIENT_NOT_FOUND, result.getStatus());
    }

    @Test
    void shouldDeclineTransferWithZeroAmount() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 100);
        int transferAmount = 0;

        // When
        var result = bankService.transfer(clientId, transferAmount);

        // Then
        assertEquals(TransactionStatus.INVALID_AMOUNT, result.getStatus());
    }

    @Test
    void shouldDeclineTransferWithNegativeAmount() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 100);
        int transferAmount = -50;

        // When
        var result = bankService.transfer(clientId, transferAmount);

        // Then
        assertEquals(TransactionStatus.INVALID_AMOUNT, result.getStatus());
    }

    @Test
    void shouldDepositMoneySuccessfully() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 200);
        int depositAmount = 300;

        // When
        var result = bankService.deposit(clientId, depositAmount);

        // Then
        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        assertEquals(500, result.getNewBalance());
    }

    @Test
    void shouldDeclineDepositForNonExistingClient() {
        // Given
        int clientId = 1;
        int depositAmount = 100;

        // When
        var result = bankService.deposit(clientId, depositAmount);

        // Then
        assertEquals(TransactionStatus.CLIENT_NOT_FOUND, result.getStatus());
    }

    @Test
    void shouldDeclineDepositWithNegativeAmount() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 100);
        int depositAmount = -200;

        // When
        var result = bankService.deposit(clientId, depositAmount);

        // Then
        assertEquals(TransactionStatus.INVALID_AMOUNT, result.getStatus());
    }

    @Test
    void shouldDeclineDepositWithZeroAmount() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 100);
        int depositAmount = 0;

        // When
        var result = bankService.deposit(clientId, depositAmount);

        // Then
        assertEquals(TransactionStatus.INVALID_AMOUNT, result.getStatus());
    }

    @Test
    void shouldReturnClientDataWhenClientExists() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 700);

        // When
        var result = bankService.getClientData(clientId);

        // Then
        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        assertEquals(700, result.getNewBalance());
    }

    @Test
    void shouldReturnClientNotFoundWhenClientDoesNotExist() {
        // Given
        int clientId = 10;

        // When
        var result = bankService.getClientData(clientId);

        // Then
        assertEquals(TransactionStatus.CLIENT_NOT_FOUND, result.getStatus());
    }

    @Test
    void balanceShouldBeCorrectAfterMultipleOperations() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 1000);

        // When
        bankService.transfer(clientId, 200);
        bankService.deposit(clientId, 300);
        bankService.transfer(clientId, 100);
        var result = bankService.getClientData(clientId);

        // Then
        assertEquals(1000, result.getNewBalance());
    }
}
