package com.example.Bank;

import com.example.Bank.Client;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ClientStorage {

    private final Map<Integer, Client> clients = new HashMap<>();

    public boolean exists(int clientId) {
        return clients.containsKey(clientId);
    }

    public boolean registerClient(int clientId, double initialBalance) {
        if (clients.containsKey(clientId)) {
            return false;
        }
        clients.put(clientId, new Client(clientId, initialBalance));
        return true;
    }

    public Optional<Client> getClient(int clientId) {
        return Optional.ofNullable(clients.get(clientId));
    }
}
