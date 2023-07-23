package com.springapp.inventoryapi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.enums.RoleType;
import com.springapp.inventoryapi.model.Address;
import com.springapp.inventoryapi.model.Customer;
import com.springapp.inventoryapi.model.User;
import com.springapp.inventoryapi.service.AddressService;
import com.springapp.inventoryapi.service.CustomerService;
import com.springapp.inventoryapi.service.UserService;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = { "http://localhost:3000" })
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserService userSerivce;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AddressService addressService;

	@PostMapping("/add")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
		/*
		 * Step : detach user from customer, encode the password, assign the role,save
		 * user in DB and re attach it to customer, detach address, save address in DB,
		 * re attach to customer and finally save the customer
		 */

		User user = customer.getUser();// detach user from customer

		user.setPassword(passwordEncoder.encode(user.getPassword()));// encode the password

		user.setRole(RoleType.CUSTOMER);// assign the role

		user = userSerivce.insert(user);// save user in DB

		customer.setUser(user);// re attach it to customer

		Address address = customer.getAddress();// detach address

		address = addressService.insert(address); // save address in DB

		customer.setAddress(address); // re attach to customer

		customer = customerService.insert(customer);

		return ResponseEntity.status(HttpStatus.OK).body(customer);
	}

	@GetMapping("/getId")
	public int getId(Principal principal) {
		return customerService.getCustomerByUsername(principal.getName()).getId();
	}

	@GetMapping("/getId/{username}")
	public int getId(@PathVariable String username) {
		return customerService.getCustomerByUsername(username).getId();
	}
}
