package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import com.example.demo.enums.AccountStatus;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", length = 4)
@Data @NoArgsConstructor @AllArgsConstructor
public  abstract class BankAccount {

	
	public BankAccount () {
		
	}
	public BankAccount(String id, double balance, Date date, AccountStatus status, Customer customer,
			List<AccountOperation> accountOperations) {
		super();
		this.id = id;
		this.balance = balance;
		this.date = date;
		this.status = status;
		this.customer = customer;
		this.accountOperations = accountOperations;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	} 
	@Id
	private String id;
	private double balance;
	private Date date;
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	@ManyToOne
	private Customer customer;
	@OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
	private List<AccountOperation> accountOperations;
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<AccountOperation> getAccountOperations() {
		return accountOperations;
	}
	public void setAccountOperations(List<AccountOperation> accountOperations) {
		this.accountOperations = accountOperations;
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
}
