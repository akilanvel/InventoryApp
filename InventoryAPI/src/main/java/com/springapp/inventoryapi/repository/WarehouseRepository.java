package com.springapp.inventoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springapp.inventoryapi.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

}
