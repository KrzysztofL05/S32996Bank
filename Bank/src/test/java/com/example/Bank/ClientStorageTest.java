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
        clientStorage.registerClient(1, 100.0);

        assertThat(clientStorage.exists(1)).isTrue();
        Optional<Client> client = clientStorage.getClient(1);
        assertThat(client).isPresent();
        assertThat(client.get().getBalance()).isEqualTo(100.0);
    }

    @Test
    void getClientNonExistingClientShouldReturnEmpty() {
        Optional<Client> client = clientStorage.getClient(42);
        assertThat(client).isEmpty();
    }

    @Test
    void existsNonExistingClientShouldReturnFalse() {
        assertThat(clientStorage.exists(42)).isFalse();
    }

    @Test
    void registerClientDuplicateShouldNotOverwrite() {
        boolean first = clientStorage.registerClient(1, 100.0);
        boolean second = clientStorage.registerClient(1, 200.0);

        assertThat(first).isTrue();
        assertThat(second).isFalse();

        Optional<Client> client = clientStorage.getClient(1);
        assertThat(client).isPresent();
        assertThat(client.get().getBalance()).isEqualTo(100.0);
    }
}
