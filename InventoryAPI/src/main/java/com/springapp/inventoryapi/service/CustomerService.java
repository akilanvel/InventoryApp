package com.springapp.inventoryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.model.Customer;
import com.springapp.inventoryapi.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	public Customer insert(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer getCustomerByUsername(String username) {
		return customerRepository.getCustomerByUsername(username);
	}

	public Customer getById(int cid) {

		return customerRepository.findById(cid).get();
	}
}
