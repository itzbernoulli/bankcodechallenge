package com.challenge.bankingly;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.challenge.bankingly.model.Customer;
import com.challenge.bankingly.model.Transaction;
import com.challenge.bankingly.repository.CustomerRepository;

@SpringBootApplication
public class BankinglyApplication implements CommandLineRunner{
	
	private static final Logger logger = LoggerFactory.getLogger(BankinglyApplication.class);

	
	@Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankinglyApplication.class, args);
	}
	
	@Override
	@Transactional
    public void run(String... strings) throws Exception {
        // save a couple of Customers
        Customer customerA = new Customer("customerA","pass");
        
		Set<Transaction> transA = new HashSet<Transaction>() {{
        	add(new Transaction(50.0,customerA));
        	add(new Transaction(40.0,customerA));
        	add(new Transaction(30.0,customerA));
        	add(new Transaction(20.0,customerA));

        }};
        customerA.setTransactions(transA);
        
 Customer customerB = new Customer("customerB","pass");
        
		Set<Transaction> transB = new HashSet<Transaction>() {{
        	add(new Transaction(5600.0,customerB));
        	add(new Transaction(40.0,customerB));
        	add(new Transaction(30.0,customerB));
        	add(new Transaction(20.0,customerB));

        }};
        customerB.setTransactions(transB);
        
        customerRepository.save(new HashSet<Customer>() {{
        	add(customerA);
        	add(customerB);
        }});
        // fetch all Customers
        for (Customer Customer : customerRepository.findAll()) {
            logger.info(Customer.toString());
        }
    }
}
