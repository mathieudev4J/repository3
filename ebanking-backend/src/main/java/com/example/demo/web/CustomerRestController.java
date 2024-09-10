package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.CustomerDTO;
import com.example.demo.entities.Customer;
import com.example.demo.services.BankAccountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class CustomerRestController {

	@Autowired
	private BankAccountService bankAccountService;
	
	public CustomerRestController(BankAccountService bankAccountService) {
		
	}
	@GetMapping("/customers")
	public List<CustomerDTO> customers(){
		return bankAccountService.listCustomer();
		
	}
}
