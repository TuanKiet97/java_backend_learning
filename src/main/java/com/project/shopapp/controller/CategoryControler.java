package com.project.shopapp.controller;

import com.project.shopapp.dtos.CategoriesDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
public class CategoryControler {
    // Hien thi tat ca categpries
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
	    @RequestParam("page") int page,
	    @RequestParam("limit") int limit
    ) {
	return ResponseEntity.ok(String.format("getAllCategories, page = %d, limit = %d", page, limit));
    }
    @PostMapping("")
    // Neu tham so truyen vao la 1 object
    public ResponseEntity<?> insertCategories(
	    @Valid  @RequestBody CategoriesDTO categoriesDTO,
	    BindingResult result) {
	if (result.hasErrors()) {
	    List<String> errorMessage = result.getFieldErrors()
		    .stream()
		    .map(FieldError::getDefaultMessage)
		    .toList();
	    return ResponseEntity.badRequest().body(errorMessage);
	}
	return ResponseEntity.ok("This is categories"  + categoriesDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategories(@PathVariable Long id) {
	return ResponseEntity.ok("Insert categories with id "+ id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id) {
	return ResponseEntity.ok("Delete the categories with id"+id);
    }
}
