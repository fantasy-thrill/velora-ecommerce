package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.Product;
import com.example.velora_ecommerce.enums.Brand;
import com.example.velora_ecommerce.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByBrandIn(List<Brand> brands, Pageable pageable);

    Page<Product> findByCategoryAndBrandIn(Category category, List<Brand> brands, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndBrandIn(String query, List<Brand> brands, Pageable pageable);
}
