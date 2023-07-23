package com.springapp.inventoryapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.dto.OrderDto;
import com.springapp.inventoryapi.model.ProductCustomer;
import com.springapp.inventoryapi.service.ProductCustomerService;

@RestController // we tell spring that this class will have rest api's
@RequestMapping("/product/customer")
@CrossOrigin(origins = { "http://localhost:3000/" })
public class ProductCustomerController {

	@Autowired
	private ProductCustomerService productCustomerService;

	@PostMapping("/purchase/{cid}")
	public void purchaseProduct(@PathVariable("cid") int cid, @RequestBody List<OrderDto> listDto) {
		productCustomerService.addPurchaseRecords(cid, listDto);
	}

	@GetMapping("/purchase/{fromDate}/{toDate}")
	public List<ProductCustomer> getAllPuchaseRecords(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate) {
		LocalDate from = LocalDate.parse(fromDate, DateTimeFormatter.ISO_DATE);
		LocalDate to = LocalDate.parse(toDate, DateTimeFormatter.ISO_DATE);

		return productCustomerService.getAllPuchaseRecords(from, to);
	}
}
