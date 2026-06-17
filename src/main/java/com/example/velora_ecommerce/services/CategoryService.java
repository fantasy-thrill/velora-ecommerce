package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.entities.Category;
import com.example.velora_ecommerce.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getAllCategoriesAlphabetically() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
