package com.example.demo.services;

import java.util.List;

import com.example.demo.dtos.CustomerDTO;
import com.example.demo.entities.BankAccount;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.Customer;
import com.example.demo.entities.SavingAccount;
import com.example.demo.repositories.BankAccountRepository;

public interface BankAccountService {

    Customer  saveCustomer (Customer customer) ;
	CurrentAccount saveCurrentBankAccount(double intialBalance, Long customerId, double overDraft) throws CustomerNotFoundException;
	SavingAccount saveSavingBankAccount(double intialBalance, Long customerId, double interestedRate) throws CustomerNotFoundException;
	List<CustomerDTO> listCustomer();
	BankAccount  getBankAccount(String accountId) throws BankAccountNotFoundException;
	void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficenceException;
	void crebit(String accountId, double amount, String description) throws BankAccountNotFoundException;
	void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficenceException;
	
}
