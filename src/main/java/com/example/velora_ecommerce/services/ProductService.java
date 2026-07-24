package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.ProductResponseDto;
import com.example.velora_ecommerce.dtos.ProductFilterDto;
import com.example.velora_ecommerce.entities.Product;
import com.example.velora_ecommerce.enums.Category;
import com.example.velora_ecommerce.mappers.ProductMapper;
import com.example.velora_ecommerce.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private LocalDate featuredDate;

    private List<ProductResponseDto> featuredProducts;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponseDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(ProductMapper::toResponseDto);
    }

    public List<ProductResponseDto> getFeaturedProducts() {
        LocalDate today = LocalDate.now();

        if (!today.equals(featuredDate)) {
            List<Product> products = productRepository.findAll();
            Collections.shuffle(products);

            featuredProducts = products.stream()
                    .limit(4)
                    .map(ProductMapper::toResponseDto)
                    .toList();

            featuredDate = today;
        }

        return featuredProducts;
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return product;
    }

    public Page<ProductResponseDto> getProductsByCategory(Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByCategory(category, pageable);

        return products.map(ProductMapper::toResponseDto);
    }

    // TODO: Write more logic for this method once administrator features are implemented.
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setDescription(updatedProduct.getDescription());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        product.setImageUrl(updatedProduct.getImageUrl());

        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductResponseDto> searchProducts(ProductFilterDto searchDto) {
        Sort springSort = Sort.unsorted();
        Page<Product> products;

        if (searchDto.getSortOption() != null) {
            switch (searchDto.getSortOption()) {
                case PRICE_LOW_TO_HIGH -> springSort = Sort.by("price").ascending();

                case PRICE_HIGH_TO_LOW -> springSort = Sort.by("price").descending();
            }
        }

        Pageable pageable = PageRequest.of(searchDto.getPage(), 12, springSort);

        if (searchDto.getBrands() == null || searchDto.getBrands().isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCase(searchDto.getQuery(), pageable);

        } else {
            products = productRepository.findByNameContainingIgnoreCaseAndBrandIn(
                    searchDto.getQuery(),
                    searchDto.getBrands(),
                    pageable
            );
        }

        return products.map(ProductMapper::toResponseDto);
    }
}
