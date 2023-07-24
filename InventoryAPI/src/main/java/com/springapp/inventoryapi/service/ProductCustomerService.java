package com.springapp.inventoryapi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.dto.OrderDto;
import com.springapp.inventoryapi.exception.ResourceNotFoundException;
import com.springapp.inventoryapi.model.Customer;
import com.springapp.inventoryapi.model.Product;
import com.springapp.inventoryapi.model.ProductCustomer;
import com.springapp.inventoryapi.repository.ProductCustomerRepository;
import com.springapp.inventoryapi.repository.ProductRepository;

@Service
public class ProductCustomerService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCustomerRepository productCustomerRepository;

	@Autowired
	private CustomerService customerService;

	@Async
	public CompletableFuture<?> addPurchaseRecords(int cid, List<OrderDto> listDto) {
		List<ProductCustomer> list = new ArrayList<>();

		listDto.stream().forEach(dto -> {
			try {
				Product p = productService.getById(dto.getId());
				Customer c = customerService.getById(cid);
				ProductCustomer pc = new ProductCustomer();
				pc.setProduct(p);
				pc.setCustomer(c);
				pc.setQuantity(dto.getQuantity());
				pc.setDateOfPurchase(LocalDate.now());
				list.add(pc);

			} catch (ResourceNotFoundException e) {

				e.printStackTrace();
			}
		});
		productCustomerRepository.saveAll(list);
		return CompletableFuture.completedFuture(list);

	}

	public List<ProductCustomer> getAllPuchaseRecords(LocalDate fromDate, LocalDate toDate) {

		return productCustomerRepository.getAllPuchaseRecords(fromDate, toDate);
	}

	public List<Product> getProductsByCustomer(Customer byUsername) {
		return productCustomerRepository.getProductsByCustomer(byUsername);
	}
}
