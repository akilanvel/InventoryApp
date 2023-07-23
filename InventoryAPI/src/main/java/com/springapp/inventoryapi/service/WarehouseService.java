package com.springapp.inventoryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.model.Warehouse;
import com.springapp.inventoryapi.repository.WarehouseRepository;

@Service
public class WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;

	public Warehouse getById(int warehouseId) {
		return warehouseRepository.findById(warehouseId).get();
	}

	public List<Warehouse> getAll() {
		// TODO Auto-generated method stub
		return warehouseRepository.findAll();
	}

}