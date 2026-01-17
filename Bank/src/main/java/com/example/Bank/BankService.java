package com.example.Bank;

import org.springframework.stereotype.Service;

@Service
public class BankService {

    private final ClientStorage clientStorage;

    public BankService(ClientStorage clientStorage) {
        this.clientStorage = clientStorage;
    }

    public TransactionResult registerClient(int clientId, double initialBalance) {

        if (initialBalance < 0) {
            return new TransactionResult(TransactionStatus.DECLINED, 0);
        }

        if (clientStorage.getClient(clientId).isPresent()) {
            return new TransactionResult(TransactionStatus.CLIENT_ALREADY_EXISTS, 0);
        }

        clientStorage.registerClient(clientId, initialBalance);
        return new TransactionResult(TransactionStatus.ACCEPTED, initialBalance);
    }

    public TransactionResult transfer(int clientId, double amount) {

        if (amount <= 0) {
            return new TransactionResult(TransactionStatus.INVALID_AMOUNT, 0);
        }

        var clientOpt = clientStorage.getClient(clientId);

        if (clientOpt.isEmpty()) {
            return new TransactionResult(TransactionStatus.CLIENT_NOT_FOUND, 0);
        }

        Client client = clientOpt.get();

        if (client.getBalance() < amount) {
            return new TransactionResult(
                    TransactionStatus.INSUFFICIENT_FUNDS,
                    client.getBalance()
            );
        }

        client.withdraw(amount);

        return new TransactionResult(
                TransactionStatus.ACCEPTED,
                client.getBalance()
        );
    }

    public TransactionResult deposit(int clientId, double amount) {

        if (amount <= 0) {
            return new TransactionResult(TransactionStatus.INVALID_AMOUNT, 0);
        }

        var clientOpt = clientStorage.getClient(clientId);

        if (clientOpt.isEmpty()) {
            return new TransactionResult(TransactionStatus.CLIENT_NOT_FOUND, 0);
        }

        Client client = clientOpt.get();
        client.deposit(amount);

        return new TransactionResult(
                TransactionStatus.ACCEPTED,
                client.getBalance()
        );
    }

    public TransactionResult getClientData(int clientId) {

        var clientOpt = clientStorage.getClient(clientId);

        if (clientOpt.isEmpty()) {
            return new TransactionResult(TransactionStatus.CLIENT_NOT_FOUND, 0);
        }

        return new TransactionResult(
                TransactionStatus.ACCEPTED,
                clientOpt.get().getBalance()
        );
    }
}
