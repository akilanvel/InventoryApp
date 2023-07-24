package com.springapp.inventoryapi.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springapp.inventoryapi.dto.MessageDto;
import com.springapp.inventoryapi.dto.OrderSheetDto;
import com.springapp.inventoryapi.enums.OrderStatus;
import com.springapp.inventoryapi.exception.ResourceNotFoundException;
import com.springapp.inventoryapi.model.Order;
import com.springapp.inventoryapi.model.Product;
import com.springapp.inventoryapi.model.Supplier;
import com.springapp.inventoryapi.model.Warehouse;
import com.springapp.inventoryapi.service.CustomerService;
import com.springapp.inventoryapi.service.OrderService;
import com.springapp.inventoryapi.service.ProductService;
import com.springapp.inventoryapi.service.SupplierService;
import com.springapp.inventoryapi.service.WarehouseService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = { "http://localhost:3000/" })
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private CustomerService customerService;

	@PostMapping("/entry")
	public ResponseEntity<?> generateOrder(@RequestBody OrderSheetDto dto, Order order) {
		/* Take input from user */
		int productId = dto.getProductId();
		int warehouseId = dto.getWarehouseId();
		int supplierId = dto.getSupplierId();
		int quantity = dto.getQuantity();

		/* Fetch objects based on ids of product, warehouse and supplier */
		Product product;
		try {
			product = productService.getById(productId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto("product id invalid"));
		}

		Warehouse warehouse = warehouseService.getById(warehouseId);

		Supplier supplier = supplierService.getById(supplierId);

		/* Create order object, attach above objects to order and save it */
		order.setQuantity(quantity);
		order.setDateOfOrder(LocalDate.now());
		order.setProduct(product);
		order.setWarehouse(warehouse);
		order.setSupplier(supplier);
		order.setStatus(OrderStatus.PENDING);
		order = orderService.insert(order);
		return ResponseEntity.status(HttpStatus.OK).body(order);

	}

	@PutMapping("/update/{oid}/{status}")
	public void updateOrderStatus(@PathVariable("oid") int oid, @PathVariable("status") String status) {
		orderService.updateOrderStatus(oid, status);
	}

	@PostMapping("/process/bulk")
	public ResponseEntity<?> processOrderBulkCsv(@RequestParam("file") MultipartFile file) {
		try {
			orderService.processOrderBulkCsv(file.getInputStream());
			return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Data Processed"));
		} catch (IOException | ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}
	}

	@GetMapping("/all/{page}/{size}") // username/password : spring has it.
	public List<Order> getAll(@PathVariable("page") int page, @PathVariable("size") int size, Principal principal) {

		Pageable pageable = PageRequest.of(page, size);
		String supplierUsername = principal.getName();
		Supplier supplier = supplierService.getByUsername(supplierUsername);
		List<Order> list = orderService.getAll(pageable, supplier.getId());
		return list;
	}

	@GetMapping("/all/{userId}/{page}/{size}") // username/password : spring has it.
	public List<Order> getBySupplier(@PathVariable("page") int page, @PathVariable("size") int size,
			@PathVariable("userId") int id) {

		Pageable pageable = PageRequest.of(page, size);
		List<Order> list = orderService.getAll(pageable, id);
		return list;
	}

	@GetMapping("/search/{string}/{page}/{size}")
	public List<Order> searchByProduct(@PathVariable("page") int page, @PathVariable("size") int size,
			@PathVariable("string") String query, Principal principal) {
		Pageable pageable = PageRequest.of(page, size);
		String supplierUsername = principal.getName();
		Supplier supplier = supplierService.getByUsername(supplierUsername);
		List<Order> list = orderService.getByProduct(pageable, supplier.getId(), query);
		return list;
	}

	@GetMapping("/search/{string}")
	public List<Order> searchBulkByProduct(@PathVariable("string") String query, Principal principal) {
		String supplierUsername = principal.getName();
		Supplier supplier = supplierService.getByUsername(supplierUsername);
		List<Order> list = orderService.getByProduct(supplier.getId(), query);
		return list;
	}

	@GetMapping("/all")
	public List<Order> searchByProduct(Principal principal) {
		return orderService.getAll(supplierService.getByUsername(principal.getName()).getId());
	}

	@GetMapping("/manager/all") // username/password : spring has it.
	public List<Order> getAllOrderForManager(
			@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size, Principal principal) {

		Pageable pageable = PageRequest.of(page, size);

		List<Order> list = orderService.getAll(pageable);
		list = list.stream().filter(o -> o.getStatus().equals(OrderStatus.DELIVERED))
				.sorted((o1, o2) -> o1.getDateOfOrder().compareTo(o2.getDateOfOrder())).collect(Collectors.toList());

		return list;
	}

}
