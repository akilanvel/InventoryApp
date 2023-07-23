package com.springapp.inventoryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.model.Supplier;
import com.springapp.inventoryapi.repository.SupplierRepository;

@Service
public class SupplierService {
	@Autowired
	private SupplierRepository supplierRepository;

	public Supplier insert(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	public Supplier getById(int supplierId) {
		return supplierRepository.findById(supplierId).get();
	}

	public Supplier getByUsername(String supplierUsername) {
		return supplierRepository.getByUsername(supplierUsername);
	}

	public List<Supplier> getAll() {
		return supplierRepository.findAll();
	}
}
