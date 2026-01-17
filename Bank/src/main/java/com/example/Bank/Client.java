package com.example.Bank;

public class Client {

    private final int id;
    private double balance;

    public Client(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance < amount) {
            return false;
        }
        balance -= amount;
        return true;
    }
}
