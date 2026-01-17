package com.example.Bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankIntegrationTest {

    @Autowired
    private BankService bankService;

    @Test
    void fullBankFlowIntegrationTest() {
        // Given
        int clientId = 1;
        bankService.registerClient(clientId, 1000);

        // When
        var transfer = bankService.transfer(clientId, 400);

        // Then
        assertEquals(TransactionStatus.ACCEPTED, transfer.getStatus());
        assertEquals(600, transfer.getNewBalance());

        // When
        var deposit = bankService.deposit(clientId, 200);

        // Then
        assertEquals(TransactionStatus.ACCEPTED, deposit.getStatus());
        assertEquals(800, deposit.getNewBalance());

        // When
        var declined = bankService.transfer(clientId, 2000);

        // Then
        assertEquals(TransactionStatus.INSUFFICIENT_FUNDS, declined.getStatus());
    }

    @Test
    void integrationShouldHandleErrorsProperly() {
        // Given
        int nonExistingClientId = 999;

        // When
        var result = bankService.transfer(nonExistingClientId, 100);

        // Then
        assertEquals(TransactionStatus.CLIENT_NOT_FOUND, result.getStatus());
    }
}
