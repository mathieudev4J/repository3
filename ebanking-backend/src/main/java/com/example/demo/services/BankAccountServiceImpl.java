package com.example.demo.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.CustomerDTO;
import com.example.demo.entities.AccountOperation;
import com.example.demo.entities.BankAccount;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.Customer;
import com.example.demo.entities.SavingAccount;
import com.example.demo.enums.OperationType;
import com.example.demo.mappers.BankAccountMapperImpl;
import com.example.demo.repositories.AccountOperationRepository;
import com.example.demo.repositories.BankAccountRepository;
import com.example.demo.repositories.CustomerRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{

	@Autowired
	private BankAccountMapperImpl dtoMapper;
	private BankAccountRepository bankAccountRepository;
	private AccountOperationRepository accountOperationRepository;
	private CustomerRepository customerRepository;
	Logger log= LoggerFactory.getLogger(this.getClass().getName());
	
	public BankAccountServiceImpl(BankAccountRepository bankAccountRepository,
			AccountOperationRepository accountOperationRepository, CustomerRepository customerRepository) {
		super();
		this.bankAccountRepository = bankAccountRepository;
		this.accountOperationRepository = accountOperationRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		log.info("Saving a new Customer");
		Customer savCustomer= customerRepository.save(customer);
		
		return savCustomer;
	}


	@Override
	public List<CustomerDTO> listCustomer() {
		
		List<Customer> customers= customerRepository.findAll();
		
		List<CustomerDTO> customerDTOs=customers.stream()
				.map(customer->dtoMapper.fromCustomer(customer))
				.collect(Collectors.toList());
		
		return customerDTOs;
	}

	@Override
	public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
		
		
		BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Account Bank not found"));
	
		return bankAccount;
		
	}

	@Override
	public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficenceException {
		// TODO Auto-generated method stub
		BankAccount bankAccount= getBankAccount(accountId);
		if(bankAccount.getBalance()<amount)
			throw new BalanceNotSufficenceException("Balance not sufficient");
	AccountOperation accountOperation= new AccountOperation();
	accountOperation.setType(OperationType.DEBIT);
	accountOperation.setAmount(amount);
	accountOperation.setOperationDate(new Date());
	accountOperation.setDescription(description);
	accountOperation.setBankAccount(bankAccount);
	accountOperationRepository.save(accountOperation);
	bankAccount.setBalance(bankAccount.getBalance()-amount);
	bankAccountRepository.save(bankAccount);
	
	}

	@Override
	public void crebit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		// TODO Auto-generated method stub
				BankAccount bankAccount= getBankAccount(accountId);
				
			AccountOperation accountOperation= new AccountOperation();
			accountOperation.setType(OperationType.DEBIT);
			accountOperation.setAmount(amount);
			accountOperation.setOperationDate(new Date());
			accountOperation.setDescription(description);
			accountOperation.setBankAccount(bankAccount);
			accountOperationRepository.save(accountOperation);
			bankAccount.setBalance(bankAccount.getBalance()+amount);
			bankAccountRepository.save(bankAccount);
		
	}

	@Override
	public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficenceException {
		// TODO Auto-generated method stub
		debit(accountIdSource, amount, "Transfer to"+accountIdDestination);
		crebit(accountIdDestination, amount, "Transert from " +accountIdSource);
	}

	@Override
	public CurrentAccount saveCurrentBankAccount(double intialBalance, Long customerId, double overDraft)
			throws CustomerNotFoundException {
		Customer customer= customerRepository.findById(customerId).orElse(null);
		if (customer ==null) throw new CustomerNotFoundException("Customer not found");		
		CurrentAccount currentAccount= new CurrentAccount();
		currentAccount.setId(UUID.randomUUID().toString());
		currentAccount.setDate(new Date());
		currentAccount.setBalance(intialBalance);
		currentAccount.setOverDraft(overDraft);
		currentAccount.setCustomer(customer);	
		
		CurrentAccount saveCurrentAccount= bankAccountRepository.save(currentAccount);
	
		// TODO Auto-generated method stub
		return saveCurrentAccount;
		// TODO Auto-generated method stub
		
	}

	@Override
	public SavingAccount saveSavingBankAccount(double intialBalance, Long customerId, double interested) throws CustomerNotFoundException {
		Customer customer= customerRepository.findById(customerId).orElse(null);
		if (customer ==null) throw new CustomerNotFoundException("Customer not found");		
		SavingAccount savingAccount= new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setDate(new Date());
		savingAccount.setBalance(intialBalance);
		savingAccount.setInterestRate(interested);
		savingAccount.setCustomer(customer);	
		
		SavingAccount saveSavingAccount= bankAccountRepository.save(savingAccount);
	
		// TODO Auto-generated method stub
		return saveSavingAccount;
	}

}
