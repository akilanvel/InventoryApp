package com.springapp.inventoryapi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springapp.inventoryapi.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findBySupplierId(int id, Pageable pageable);

	@Query("SELECT o FROM Order o JOIN o.product p WHERE o.supplier.id = ?1 AND p.title LIKE %?2%")
	List<Order> findOrdersByProductTitleAndSupplierId(int supplierId, String searchString, Pageable pageable);

	List<Order> findBySupplierId(int id);

	@Query("SELECT o FROM Order o JOIN o.product p WHERE o.supplier.id = ?1 AND p.title LIKE %?2%")
	List<Order> findOrdersByProductTitleAndSupplierId(int supplierId, String searchString);
}