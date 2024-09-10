package com.example.demo;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.entities.AccountOperation;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.Customer;
import com.example.demo.entities.SavingAccount;
import com.example.demo.enums.AccountStatus;
import com.example.demo.enums.OperationType;
import com.example.demo.repositories.AccountOperationRepository;

import com.example.demo.repositories.BankAccountRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.BankAccountService;
import com.example.demo.services.CustomerNotFoundException;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}
	
	//@Bean
	CommandLineRunner start (BankAccountService bankAccountService) {
		return args ->{
			Stream.of("Pierre", "Jacques", "Mohamed", "Rémi", "Robert").forEach(name->{
		Customer customer= new Customer();
		customer.setName(name);
		customer.setEmail(name+"gmail.com");
		bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listCustomer().forEach(customer-> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*9000, customer.getId(), 9000);
					bankAccountService.saveSavingBankAccount(Math.random()*9000, customer.getId(), 9000);
				} catch (CustomerNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		};
	}
	@Bean
CommandLineRunner start (CustomerRepository customerRepository,
        BankAccountRepository bankAccountRepository,
        AccountOperationRepository  accountBankRepository) {
	return args ->{
		Stream.of("Pierre", "Jacques", "Mohamed", "Rémi", "Robert").forEach(name->{
			Customer customer= new Customer();
	customer.setName(name);
	customer.setEmail(name+"gmail.com");
	customerRepository.save(customer);
		});
		customerRepository.findAll().forEach(cust-> {
			CurrentAccount currentAccount= new CurrentAccount();
			currentAccount.setId(UUID.randomUUID().toString());
			currentAccount.setBalance(Math.random()*9000);
			currentAccount.setDate(new Date());
			currentAccount.setStatus(AccountStatus.CREATED);
			currentAccount.setCustomer(cust);
			currentAccount.setOverDraft(9000);
			
		    bankAccountRepository.save(currentAccount);
		    
			SavingAccount savingAccount= new SavingAccount();
			savingAccount.setId(UUID.randomUUID().toString());
			savingAccount.setBalance(Math.random()*9000);
			savingAccount.setDate(new Date());
			savingAccount.setStatus(AccountStatus.CREATED);
			savingAccount.setCustomer(cust);
			savingAccount.setInterestRate(5.5);
			bankAccountRepository.save(savingAccount);
		    		    
		});
		bankAccountRepository.findAll().forEach(acc-> {
			for (int i=0; i<10;i++) {
				AccountOperation accountOperation= new AccountOperation();
				accountOperation.setOperationDate(new Date());
				accountOperation.setAmount(Math.random()*12000);
				accountOperation.setType(Math.random()>0.5 ? OperationType.DEBIT : OperationType.CREDIT);
				accountOperation.setBankAccount(acc);
				accountBankRepository.save(accountOperation);
			}
		});
	};
}
}
