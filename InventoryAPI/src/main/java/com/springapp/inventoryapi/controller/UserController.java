package com.springapp.inventoryapi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.model.User;
import com.springapp.inventoryapi.service.SupplierService;
import com.springapp.inventoryapi.service.UserService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000/" })
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	SupplierService supplierService;

	@GetMapping("/user/login")
	public User login(Principal principal) {
		String username = principal.getName();
		return (User) userService.loadUserByUsername(username);
	}

	@GetMapping("/user/getSupplierId/{username}")
	public ResponseEntity<?> getSupplierId(@PathVariable("username") String username) {
		return ResponseEntity.status(HttpStatus.OK).body(supplierService.getByUsername(username));
	}
}
