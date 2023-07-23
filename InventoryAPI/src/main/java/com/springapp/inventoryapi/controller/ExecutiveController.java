package com.springapp.inventoryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.enums.RoleType;
import com.springapp.inventoryapi.model.Address;
import com.springapp.inventoryapi.model.Executive;
import com.springapp.inventoryapi.model.Order;
import com.springapp.inventoryapi.model.Supplier;
import com.springapp.inventoryapi.model.User;
import com.springapp.inventoryapi.service.AddressService;
import com.springapp.inventoryapi.service.ExecutiveService;
import com.springapp.inventoryapi.service.OrderService;
import com.springapp.inventoryapi.service.SupplierService;
import com.springapp.inventoryapi.service.UserService;

@RestController
@RequestMapping("/executive")
public class ExecutiveController {
	@Autowired
	private ExecutiveService executiveService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userSerivce;
	@Autowired
	private OrderService orderService;

	@PostMapping("/supplier/add")
	public Supplier postSupplier(@RequestBody Supplier supplier) {
		/*
		 * Step 1: Detach the address, save the address, attach the saved object back to
		 * supplier
		 */
		Address address = supplier.getAddress(); // Detach the address
		address = addressService.insert(address); // save the address
		supplier.setAddress(address); // attach the saved object back to supplier

		/*
		 * Step 2: Detach the user, encode the password, set the role, save the user,
		 * re-attach to supplier
		 */
		User user = supplier.getUser(); // Detach the user
		user.setPassword(passwordEncoder.encode(user.getPassword())); // encode the password
		user.setRole(RoleType.SUPPLIER); // set the role
		user = userSerivce.insert(user); // save the user
		supplier.setUser(user); // re-attach to supplier

		/* Step 3: save the supplier */
		supplier = supplierService.insert(supplier);
		return supplier;
	}

	@PostMapping("/add")
	public Executive postExecutive(@RequestBody Executive executive) {
		/*
		 * Step 1: Detach the user, encode the password, set the role, save the user,
		 * re-attach to executive
		 */
		User user = executive.getUser(); // Detach the user
		user.setPassword(passwordEncoder.encode(user.getPassword())); // encode the password
		user.setRole(RoleType.EXECUTIVE); // set the role
		user = userSerivce.insert(user); // save the user
		executive.setUser(user); // re-attach to supplier
		/* Step 3: save the executive */
		executive = executiveService.insert(executive);
		return executive;
	}

	@GetMapping("/order/all/{supplierId}") // username/password : spring has it.
	public List<Order> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10000") Integer size,
			@PathVariable("supplierId") int supplierId) {

		Pageable pageable = PageRequest.of(page, size);

		Supplier supplier = supplierService.getById(supplierId);
		List<Order> list = orderService.getAll(pageable, supplier.getId());
		return list;
	}
}
