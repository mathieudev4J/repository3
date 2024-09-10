package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.AccountOperation;
import com.example.demo.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, String>{

}
