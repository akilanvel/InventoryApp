package com.springapp.inventoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springapp.inventoryapi.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	@Query("select m from Manager m where m.user.username=?1")
	Manager getByUsername(String username);
}
