package com.example.Bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

	public BankApplication(BankService bankService) {

		bankService.registerClient(1, 1000);
		bankService.registerClient(1,2);
		bankService.registerClient(2, 200);
		bankService.registerClient(3, 450);

		System.out.println(bankService.transfer(1, 300).getStatus());
		System.out.println(bankService.transfer(2, 500).getStatus());
		System.out.println(bankService.transfer(3, 450).getStatus());
		System.out.println(bankService.deposit(2, 400).getNewBalance());
		System.out.println(bankService.deposit(3, 2137).getStatus());
		System.out.println(bankService.getClientData(1).getNewBalance());
		System.out.println(bankService.getClientData(3).getNewBalance());
	}

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
}

