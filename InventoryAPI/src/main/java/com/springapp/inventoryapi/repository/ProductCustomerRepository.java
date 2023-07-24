package com.springapp.inventoryapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springapp.inventoryapi.model.Customer;
import com.springapp.inventoryapi.model.Product;
import com.springapp.inventoryapi.model.ProductCustomer;

public interface ProductCustomerRepository extends JpaRepository<ProductCustomer, Long> {

	@Query("select pc from ProductCustomer pc where pc.dateOfPurchase >= ?1 AND pc.dateOfPurchase<=?2")
	List<ProductCustomer> getAllPuchaseRecords(LocalDate fromDate, LocalDate toDate);

	//@Query("SELECT p FROM Product p JOIN p.productCustomers pc WHERE pc.customer = :customer")
	List<Product> getProductsByCustomer(Customer customer);

}
