package com.springapp.inventoryapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.dto.OrderDto;
import com.springapp.inventoryapi.exception.ResourceNotFoundException;
import com.springapp.inventoryapi.model.Customer;
import com.springapp.inventoryapi.model.Product;
import com.springapp.inventoryapi.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAll() {

		return productRepository.findAll();
	}

	public void deleteProduct(int pid) {
		productRepository.deleteById(pid);

	}

	public Product insert(Product product) {
		return productRepository.save(product);

	}

	public Product getById(int pid) throws ResourceNotFoundException {
		Optional<Product> optional = productRepository.findById(pid);
		if (!optional.isPresent())
			throw new ResourceNotFoundException("Product ID invalid");
		return optional.get();
	}

	public List<Product> searchProduct(String qString) {
		return productRepository.searchProduct(qString);
	}

	public int computeAvailableQuantity(int pid) {
		return productRepository.computeAvailableQuantity(pid);
	}

	public Map<Integer, Boolean> placeOrderComputation(List<OrderDto> listDto) {
		Map<Integer, Boolean> map = new HashMap<>();
		/* Step 1: Iterate over the list Dto and compute quantity for each product */
		listDto.parallelStream().forEach(dto -> {
			int pid = dto.getId();
			int quantityOrdered = dto.getQuantity();

			/* Go to DB and fetch totalQuantity available of this product */
			int availableQuantity = productRepository.findById(pid).get().getTotalQuantity();
			if (availableQuantity >= quantityOrdered)
				map.put(pid, true);
			else
				map.put(pid, false);
		});
		return map;

	}

	public void confirmOrder(List<OrderDto> listDto, Customer customer) {
		listDto.parallelStream().forEach(dto -> {
			int pid = dto.getId();
			int quantityOrdered = dto.getQuantity();
			/* Step 1: update product quantity for each product purchase */

			Product product = productRepository.findById(pid).get(); // fetch

			product.setTotalQuantity(product.getTotalQuantity() - quantityOrdered); // update

			product = productRepository.save(product); // save

			/* Step 2: Make an entry in outward register for each product purchase */

		});

	}

	public List<Product> getAllByCategoryId(int cid) {
		return productRepository.getAllByCategoryId(cid);
	}

}