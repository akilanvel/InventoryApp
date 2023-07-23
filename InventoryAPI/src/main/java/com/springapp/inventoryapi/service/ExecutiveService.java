package com.springapp.inventoryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.model.Executive;
import com.springapp.inventoryapi.repository.ExecutiveRepository;

@Service
public class ExecutiveService {
	@Autowired
	private ExecutiveRepository executiveRepository;

	public Executive insert(Executive executive) {
		return executiveRepository.save(executive);
	}
}
