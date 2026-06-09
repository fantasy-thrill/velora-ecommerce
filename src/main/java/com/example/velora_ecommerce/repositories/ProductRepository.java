package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.Category;
import com.example.velora_ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByCategory(Category category);

    public List<Product> findByNameContainingIgnoreCase(String keyword);
}
