package com.springapp.inventoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springapp.inventoryapi.model.InwardRegister;

public interface InwardRegisterRepository extends JpaRepository<InwardRegister, Integer> {

}
