package com.springapp.inventoryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springapp.inventoryapi.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
