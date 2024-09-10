package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import com.example.demo.enums.AccountStatus;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("SA")
public class SavingAccount extends BankAccount{
	
	
	public SavingAccount() {
		
	}
   public SavingAccount(String id, double balance, Date date, AccountStatus status, Customer customer,
			List<AccountOperation> accountOperations) {
		super(id, balance, date, status, customer, accountOperations);
		// TODO Auto-generated constructor stub
	}
private double interestRate;

 /* public SavingAccount( double interestRate) {
	  this.setInterestRate(interestRate);
  }*/
 
public double getInterestRate() {
	return interestRate;
}
public void setInterestRate(double interestRate) {
	this.interestRate = interestRate;
}
}
