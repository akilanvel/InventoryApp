package com.springapp.inventoryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.model.Address;
import com.springapp.inventoryapi.repository.AddressRepository;

@Service
public class AddressService {
	@Autowired
	private AddressRepository addressRepository;

	public Address insert(Address address) {
		return addressRepository.save(address);
	}
}
