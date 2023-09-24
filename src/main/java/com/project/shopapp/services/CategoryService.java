package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoriesDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CatetoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CatetoryRepository catetoryRepository;
    @Override
    public Category createCategory(CategoriesDTO categoriesDTO) {
	Category newCategory = Category
		.builder()
		.name(categoriesDTO.getName())
		.build();
	return catetoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) {
	return catetoryRepository.findById(id)
		.orElseThrow(()->new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategory() {
	return catetoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long categoryId, CategoriesDTO categoriesDTO) {
	Category existCategory = getCategoryById(categoryId);
	existCategory.setName(categoriesDTO.getName());
	catetoryRepository.save(existCategory);
	return existCategory;
    }

    @Override
    public void deleteCategory(Long id) {
	catetoryRepository.deleteById(id);
    }
}
