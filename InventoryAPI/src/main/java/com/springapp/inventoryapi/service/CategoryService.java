package com.springapp.inventoryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapp.inventoryapi.exception.ResourceNotFoundException;
import com.springapp.inventoryapi.model.Category;
import com.springapp.inventoryapi.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Category insert(Category category) {
		return categoryRepository.save(category);
	}

	public List<Category> getAllCategories() {
		List<Category> list = categoryRepository.findAll();
		list = list.stream().sorted((c1, c2) -> c1.getPriority() - c2.getPriority()).collect(Collectors.toList());
		return list;
	}

	public Category getById(int id) throws ResourceNotFoundException {
		Optional<Category> optional = categoryRepository.findById(id);
		if (!optional.isPresent())
			throw new ResourceNotFoundException("Category ID invalid");
		return optional.get();
	}

	public void delete(Category categoryDb) {
		categoryRepository.delete(categoryDb);

	}

}
