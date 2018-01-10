package com.challenge.bankingly.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.bankingly.model.Customer;
import com.challenge.bankingly.model.Transaction;
import com.challenge.bankingly.repository.CustomerRepository;


@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
	    return customerRepository.findAll();
	}
	
	// Customer SignUp
	@PostMapping("/customers")
	public Customer customerSignup(@Valid @RequestBody Customer customer) {
	    return customerRepository.save(customer);
	}
		
	 // Get a Single Customer
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getTransactions(@PathVariable(value = "id") int customerId) {
	    Customer customer = customerRepository.findOne(customerId);
	    if(customer == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(customer);
	}

	@PostMapping("/deposit/{id}")
	public ResponseEntity<Customer> makeDeposit(@PathVariable(value = "id") int customerId, @RequestBody Map<String, String> json) {
		Double amount =  Double.parseDouble(json.get("amount"));
		Customer customer = customerRepository.findOne(customerId);
	    if(customer == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    if((amount != null) && (amount > 0)) {
	    	Set<Transaction> trans = customer.getTransactions();
		    trans.add(new Transaction(amount,customer));
		    customer.setTransactions(trans);
		    Customer updatedCustomer = customerRepository.save(customer);
		    return ResponseEntity.ok(updatedCustomer);
	    }else {
	    	return ResponseEntity.ok(customer);
	    }
	    
	}
	
	@PostMapping("/withdrawal/{id}")
	public ResponseEntity<Customer> makeWithdrawal(@PathVariable(value = "id") int customerId, @RequestBody Map<String, String> json) {
		Double amount =  Double.parseDouble(json.get("amount"));
		Customer customer = customerRepository.findOne(customerId);
	    if(customer == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    if(amount < customer.getBalance()) {
	    	Set<Transaction> trans = customer.getTransactions();
		    trans.add(new Transaction(-amount,customer));
		    customer.setTransactions(trans);
		    Customer updatedCustomer = customerRepository.save(customer);
		    return ResponseEntity.ok(updatedCustomer);
	    }else {
	    	return ResponseEntity.ok(customer);
	    }
	    
	}
		
	@GetMapping("/balance/{id}")
	public ResponseEntity<String> getCustomerBalance(@PathVariable(value = "id") int customerId) {
		Customer customer = customerRepository.findOne(customerId);
	    if(customer == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body("Your account balance is: " + customer.getBalance());
	}
	
}
