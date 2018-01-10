package com.challenge.bankingly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.bankingly.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
