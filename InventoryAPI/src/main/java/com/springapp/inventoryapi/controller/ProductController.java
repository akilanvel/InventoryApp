package com.springapp.inventoryapi.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.dto.MessageDto;
import com.springapp.inventoryapi.dto.OrderDto;
import com.springapp.inventoryapi.exception.ResourceNotFoundException;
import com.springapp.inventoryapi.model.Category;
import com.springapp.inventoryapi.model.Customer;
import com.springapp.inventoryapi.model.Product;
import com.springapp.inventoryapi.service.CategoryService;
import com.springapp.inventoryapi.service.CustomerService;
import com.springapp.inventoryapi.service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = { "http://localhost:3000/" })
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CustomerService customerService;

	@PostMapping("/add/{catID}")
	public ResponseEntity<?> insertProduct(@PathVariable("catID") int catID, @RequestBody Product product) { // check if
																												// there
																												// is
																												// any
																												// other
																												// class
																												// injected
																												// in
																												// Product
																												// Model
		// since category is injected, we need categoryId from the UI
		try {
			Category category = categoryService.getById(catID);
			product.setCategory(category);
			product = productService.insert(product);

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}

		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		List<Product> list = productService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@DeleteMapping("/delete/{pid}")
	public ResponseEntity<?> deleteProduct(@PathVariable("pid") int pid) {
		productService.deleteProduct(pid);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("product deleted"));
	}

	@PutMapping("/update/{pid}")
	public ResponseEntity<?> updateProduct(@PathVariable("pid") int pid, @RequestBody Product updatedProduct) {
		try {
			Product productDB = productService.getById(pid);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}
		// productDB has id , updatedProduct does not id but has updated info
		updatedProduct.setId(pid);
		productService.insert(updatedProduct);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Product updated"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") int id) {
		try {
			Product product = productService.getById(id);
			/* Get Reviews By product id */

			return ResponseEntity.status(HttpStatus.OK).body(product);

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}
	}

	@GetMapping("/search/{qString}")
	public List<Product> searchProduct(@PathVariable("qString") String qString) {
		List<Product> list = productService.searchProduct(qString);
		return list;
	}

	@PostMapping("/place-order")
	public Map<Integer, Boolean> placeOrder(@RequestBody List<OrderDto> listDto) {
		return productService.placeOrderComputation(listDto);
	}

	@PostMapping("/confirm-order")
	public void confirmOrder(@RequestBody List<OrderDto> listDto, Principal principal) {
		String username = principal.getName();
		Customer customer = customerService.getCustomerByUsername(username);
		productService.confirmOrder(listDto, customer);
		// Todo from here....
		// need to figure out warehouse id on the basis of customer zipcode.
		// warehouse_zip_mapping(warehouse_id,zipcode) : 1: 1000 zipcode
		//
	}

	@GetMapping("/category/all/{cid}")
	public List<Product> getAllByCategoryId(@PathVariable("cid") int cid) {
		return productService.getAllByCategoryId(cid);
	}
}