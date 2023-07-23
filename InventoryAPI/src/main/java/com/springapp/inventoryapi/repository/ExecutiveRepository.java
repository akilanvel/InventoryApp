package com.springapp.inventoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springapp.inventoryapi.model.Executive;

public interface ExecutiveRepository extends JpaRepository<Executive, Integer> {

}
