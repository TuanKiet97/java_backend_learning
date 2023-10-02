package com.project.shopapp.controller;

import com.project.shopapp.dtos.CategoriesDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("${api.prefix}/categories")
//@Validated
@RequiredArgsConstructor
public class CategoryControler {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> createCategories(
	    @Valid @RequestBody CategoriesDTO categoriesDTO,
	    BindingResult result) {
	if (result.hasErrors()) {
	    List<String> errorMessage = result.getFieldErrors()
		    .stream()
		    .map(FieldError::getDefaultMessage)
		    .toList();
	    return ResponseEntity.badRequest().body(errorMessage);
	}
	categoryService.createCategory(categoriesDTO);
	return ResponseEntity.ok("Insert category successfully");
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(
	    @RequestParam("page") int page,
	    @RequestParam("limit") int limit
    ) {
	List<Category> categories = categoryService.getAllCategory();
	return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategories(
	    @PathVariable Long id,
	    @RequestBody CategoriesDTO categoriesDTO) {
	categoryService.updateCategory(id, categoriesDTO);
	return ResponseEntity.ok("Update categories successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id) {
	categoryService.deleteCategory(id);
	return ResponseEntity.ok("Delete the categories successfully");
    }
}
