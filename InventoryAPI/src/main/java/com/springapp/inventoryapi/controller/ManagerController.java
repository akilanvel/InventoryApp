package com.springapp.inventoryapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.enums.RoleType;
import com.springapp.inventoryapi.model.Manager;
import com.springapp.inventoryapi.model.User;
import com.springapp.inventoryapi.service.ManagerService;
import com.springapp.inventoryapi.service.UserService;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = { "http://localhost:3000/" })
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userSerivce;

	@PostMapping("/sign-up")
	public Manager postManager(@RequestBody Manager manager) {
		User user = manager.getUser(); // Detach the user
		user.setPassword(passwordEncoder.encode(user.getPassword())); // encode the password
		user.setRole(RoleType.MANAGER); // set the role
		user = userSerivce.insert(user); // save the user
		manager.setUser(user); // re-attach to supplier

		return managerService.insert(manager);
	}
}
