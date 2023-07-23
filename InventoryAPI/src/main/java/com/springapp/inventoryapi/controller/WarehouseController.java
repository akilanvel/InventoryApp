package com.springapp.inventoryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.model.Warehouse;
import com.springapp.inventoryapi.service.WarehouseService;

import com.springapp.inventoryapi.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
@CrossOrigin(origins = { "http://localhost:3000/" })
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;

	@GetMapping("/all")
	public List<Warehouse> getAll() {
		return warehouseService.getAll();
	}
}
