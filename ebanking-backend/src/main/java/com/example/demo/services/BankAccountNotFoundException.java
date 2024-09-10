package com.example.demo.services;

public class BankAccountNotFoundException extends Exception {
	public  BankAccountNotFoundException(String message) {
		super (message);
	}
}
