package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoriesDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CatetoryRepository;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoriesDTO categoriesDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategory();

    Category updateCategory(Long categoryId, CategoriesDTO category);

    void deleteCategory(Long id);
}
