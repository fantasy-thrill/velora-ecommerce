package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    List<Category> getAllCategories();

    List<Category> getAllCategoriesAlphabetically();

    void deleteCategory(Long id);
}
