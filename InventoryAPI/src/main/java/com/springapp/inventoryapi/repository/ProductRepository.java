package com.springapp.inventoryapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springapp.inventoryapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("select p from Product p where p.title LIKE %?1%")
	List<Product> searchProduct(String qString);

	@Query(value = "select SUM(quantity) from inward_register where product_id=?1", nativeQuery = true)
	int computeAvailableQuantity(int pid);

	@Query("select p from Product p where p.category.id=?1")
	List<Product> getAllByCategoryId(int cid);

}
