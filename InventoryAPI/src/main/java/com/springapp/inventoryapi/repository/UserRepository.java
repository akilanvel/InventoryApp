package com.springapp.inventoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springapp.inventoryapi.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
	Boolean existsByUsername(String username);

}
