package com.springapp.inventoryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.enums.RoleType;
import com.springapp.inventoryapi.model.Address;
import com.springapp.inventoryapi.model.Supplier;
import com.springapp.inventoryapi.model.User;
import com.springapp.inventoryapi.service.AddressService;
import com.springapp.inventoryapi.service.SupplierService;
import com.springapp.inventoryapi.service.UserService;

@RestController
@RequestMapping("/supplier")
@CrossOrigin(origins = { "http://localhost:3000/" })
public class SupplierController {
	@Autowired
	private SupplierService supplierService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/all")
	public List<Supplier> getAll() {
		return supplierService.getAll();
	}

	@PostMapping("/sign-up")
	public Supplier signUp(@RequestBody Supplier supplier) {
		User user = supplier.getUser();

		user.setPassword(passwordEncoder.encode(user.getPassword()));// encode the password

		user.setRole(RoleType.SUPPLIER);// assign the role

		user = userService.insert(user);// save user in DB

		supplier.setUser(user);// re attach it to customer

		Address address = supplier.getAddress();// detach address

		address = addressService.insert(address); // save address in DB

		supplier.setAddress(address); // re attach to customer

		return supplierService.insert(supplier);
	}
}
