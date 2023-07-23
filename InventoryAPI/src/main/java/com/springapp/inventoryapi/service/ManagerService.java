package com.springapp.inventoryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.model.Manager;
import com.springapp.inventoryapi.repository.ManagerRepository;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;

	public Manager insert(Manager manager) {
		return managerRepository.save(manager);
	}

	public Manager getByUsername(String username) {

		return managerRepository.getByUsername(username);
	}
}