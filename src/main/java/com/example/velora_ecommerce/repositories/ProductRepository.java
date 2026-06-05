package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
