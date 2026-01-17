package com.example.Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ClientStorageTest {

    private ClientStorage clientStorage;

    @BeforeEach
    void setUp() {
        clientStorage = new ClientStorage();
    }

    @Test
    void registerClientShouldAddClient() {
        // Given
        int clientId = 1;
        double initialBalance = 100.0;

        // When
        clientStorage.registerClient(clientId, initialBalance);

        // Then
        assertThat(clientStorage.exists(clientId)).isTrue();

        Optional<Client> client = clientStorage.getClient(clientId);
        assertThat(client).isPresent();
        assertThat(client.get().getBalance()).isEqualTo(initialBalance);
    }

    @Test
    void getClientNonExistingClientShouldReturnEmpty() {
        // Given
        int nonExistingClientId = 42;

        // When
        Optional<Client> client = clientStorage.getClient(nonExistingClientId);

        // Then
        assertThat(client).isEmpty();
    }

    @Test
    void existsNonExistingClientShouldReturnFalse() {
        // Given
        int nonExistingClientId = 42;

        // When
        boolean exists = clientStorage.exists(nonExistingClientId);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void registerClientDuplicateShouldNotOverwrite() {
        // Given
        int clientId = 1;
        double firstBalance = 100.0;
        double secondBalance = 200.0;

        // When
        boolean first = clientStorage.registerClient(clientId, firstBalance);
        boolean second = clientStorage.registerClient(clientId, secondBalance);

        // Then
        assertThat(first).isTrue();
        assertThat(second).isFalse();

        Optional<Client> client = clientStorage.getClient(clientId);
        assertThat(client).isPresent();
        assertThat(client.get().getBalance()).isEqualTo(firstBalance);
    }
}
