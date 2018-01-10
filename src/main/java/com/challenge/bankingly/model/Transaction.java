package com.challenge.bankingly.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Transaction {
	private int id;
	private Double amount;
	private Customer customer;
	
	public Transaction() {
	}
	
	public Transaction(Double amount) {
		this.amount = amount;
	}
	
	public Transaction(Double amount, Customer customer) {
		this.amount = amount;
		this.customer = customer;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	 public void setId(int id) {
	        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    public Customer getCustomer() {
    	return customer;
    }
    
    public void setCustomer(Customer customer) {
    	this.customer = customer;
    }
    
}

