package com.springapp.inventoryapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.enums.OrderStatus;
import com.springapp.inventoryapi.exception.ResourceNotFoundException;
import com.springapp.inventoryapi.model.Order;
import com.springapp.inventoryapi.model.Product;
import com.springapp.inventoryapi.model.Supplier;
import com.springapp.inventoryapi.model.Warehouse;
import com.springapp.inventoryapi.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private InwardRegisterService inwardRegisterService;

	@Autowired
	private ProductService productService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private SupplierService supplierService;

	public Order insert(Order order) {
		return orderRepository.save(order);
	}

	public void updateOrderStatus(int oid, String status) {
		OrderStatus ostatus = OrderStatus.valueOf(status);
		Order order = orderRepository.findById(oid).get();
		switch (ostatus) {
		case ACCEPTED:
			order.setStatus(OrderStatus.ACCEPTED);
			orderRepository.save(order);
			break;
		case DELIVERED:
			order.setStatus(OrderStatus.DELIVERED);
			orderRepository.save(order);
			inwardRegisterService.insert(order);
			break;
		case REJECTED:
			order.setStatus(OrderStatus.REJECTED);
			orderRepository.save(order);
			break;
		case PENDING:
			order.setStatus(OrderStatus.PENDING);
			orderRepository.save(order);
			break;
		case APPROVED:
			order.setStatus(OrderStatus.APPROVED);
			orderRepository.save(order);
			inwardRegisterService.insert(order);
			break;
		case RECEIVED:
			order.setStatus(OrderStatus.RECEIVED);
			orderRepository.save(order);
			break;
		}

	}

	@Async
	public CompletableFuture<?> processOrderBulkCsv(InputStream inputStream)
			throws IOException, ResourceNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		br.readLine(); // to ignore the headings
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String line = "";
		List<Order> list = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			String[] sarry = line.split(",");
			LocalDate dateOfOrder = LocalDate.parse(sarry[0], formatter);
			int quantity = Integer.parseInt(sarry[1]);
			int productId = Integer.parseInt(sarry[2]);
			int supplierId = Integer.parseInt(sarry[3]);
			int warehouseId = Integer.parseInt(sarry[4]);
			OrderStatus status = OrderStatus.valueOf(sarry[5]);

			Product product = productService.getById(productId);
			Warehouse warehouse = warehouseService.getById(warehouseId);
			Supplier supplier = supplierService.getById(supplierId);

			Order order = new Order();
			order.setQuantity(quantity);
			order.setDateOfOrder(dateOfOrder);
			order.setProduct(product);
			order.setWarehouse(warehouse);
			order.setSupplier(supplier);
			order.setStatus(status);
			list.add(order);
		}

		/* Save list in DB using Multi threading */
		list = orderRepository.saveAll(list); // main T1--T4
		/*
		 * This saveAll, by default, is designed to be a serial operation using single
		 * thread. you have to tell spring to perform this op using multi thread in
		 * Asynchronous manner
		 */

		// this CompletableFuture interface ensures that main thread waits till the
		// above list
		// completes its operation done by all the treads.

		return CompletableFuture.completedFuture(list);
	}

	public List<Order> getAll(Pageable pageable, int id) {
		// findAll(pageable gives you Page<Order>)
		// to convert it into list, use getContent() method
		List<Order> list = orderRepository.findBySupplierId(id, pageable);
		return list;
	}

	public List<Order> getByProduct(Pageable pageable, int id, String query) {
		List<Order> list = orderRepository.findOrdersByProductTitleAndSupplierId(id, query, pageable);
		return list;
	}

	public List<Order> getAll(int id) {
		return orderRepository.findBySupplierId(id);
	}

	public List<Order> getByProduct(int id, String query) {
		return orderRepository.findOrdersByProductTitleAndSupplierId(id, query);
	}

	public List<Order> getAll(Pageable pageable) {

		return orderRepository.findAll(pageable).getContent();
	}
}
