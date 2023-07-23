package com.springapp.inventoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springapp.inventoryapi.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
