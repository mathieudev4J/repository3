package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import com.example.demo.enums.AccountStatus;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CA")
public class CurrentAccount extends BankAccount{

	public CurrentAccount() {
		
	}
	
	public CurrentAccount(String id, double balance, Date date, AccountStatus status, Customer customer,
			List<AccountOperation> accountOperations) {
		super(id, balance, date, status, customer, accountOperations);
		// TODO Auto-generated constructor stub
	}

	public double getOverDraft() {
		return overDraft;
	}

	public void setOverDraft(double overDraft) {
		this.overDraft = overDraft;
	}

	private double overDraft;
}
