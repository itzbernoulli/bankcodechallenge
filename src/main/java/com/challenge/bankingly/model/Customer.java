package com.challenge.bankingly.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
	private int id;
	private String email;
	private String password;
	private Set<Transaction> transactions;
	@Transient
	private Transaction singleTransaction;
	
	public Customer() {}
	
	public Customer(String email, String password){
		this.email = email;
		this.password = password;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
	        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    @Transient
    public Double getBalance() {
    	transactions = this.getTransactions();
    	Double total = 0.0;
    	if((transactions != null)  && (transactions.size() > 0) ) {
    		for(Transaction trans : transactions) {
    			total += trans.getAmount();
    		}
    	}
    	return total;
    }

   
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    public Set<Transaction> getTransactions() {
    	return transactions;
    }
    
    public void setTransactions(Set<Transaction> transactions) {
    	this.transactions = transactions;
    }
    
    @Override
    public String toString() {
    	String result = String.format(
    			"Customer[id=%d, email='%s']", id, email);
    	if(transactions != null) {
    		for(Transaction trans : transactions) {
    			result += String.format("Transaction[id=%d, amount='%s']", trans.getId(),trans.getAmount());
    		}
    	}
    	return result;
    }
    
}
