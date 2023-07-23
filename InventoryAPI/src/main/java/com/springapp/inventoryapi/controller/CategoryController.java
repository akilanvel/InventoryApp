package com.springapp.inventoryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.inventoryapi.dto.MessageDto;
import com.springapp.inventoryapi.exception.ResourceNotFoundException;
import com.springapp.inventoryapi.model.Category;
import com.springapp.inventoryapi.service.CategoryService;

@RestController // we tell spring that this class will have rest api's
@RequestMapping("/category")
@CrossOrigin(origins = { "http://localhost:3000/" })
public class CategoryController {
	@Autowired
	private CategoryService categoryService; // inject service in controller using @autowired

	@PostMapping("/add")
	public Category insertCategory(@RequestBody Category category) {
		// Reach out to service layer and insert this object
		category = categoryService.insert(category);
		return category;

	}

	@GetMapping("/all")
	public List<Category> getAllCategories() {
		List<Category> list = categoryService.getAllCategories();
		return list;
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody Category updatedCategory) {
		/* Step 1: fetch category object on the basis of this id */
		Category categoryDb = null;
		try {
			categoryDb = categoryService.getById(id);
			categoryDb.setName(updatedCategory.getName());
			categoryDb.setPriority(updatedCategory.getPriority());
			categoryDb = categoryService.insert(categoryDb);

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}

		return ResponseEntity.status(HttpStatus.OK).body(categoryDb);
	}

	// @RequestMapping("/category")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
		/* Step 1: Fetch the object by id */
		Category categoryDb = null;
		try {
			categoryDb = categoryService.getById(id);
			categoryService.delete(categoryDb);
			return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Category deleted"));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}
	}
}
