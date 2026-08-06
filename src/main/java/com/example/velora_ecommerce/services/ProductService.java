package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.ProductResponseDto;
import com.example.velora_ecommerce.dtos.ProductFilterDto;
import com.example.velora_ecommerce.entities.Product;
import com.example.velora_ecommerce.enums.Brand;
import com.example.velora_ecommerce.enums.Category;
import com.example.velora_ecommerce.enums.SortOption;
import com.example.velora_ecommerce.mappers.ProductMapper;
import com.example.velora_ecommerce.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private LocalDate featuredDate;

    private List<ProductResponseDto> featuredProducts;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponseDto> getAllProducts(ProductFilterDto filterDto) {
        Sort sort = getSort(filterDto.getSortOption());

        Pageable pageable = PageRequest.of(filterDto.getPage(), 12, sort);

        Category category = filterDto.getCategory();
        List<Brand> brands = filterDto.getBrands();

        boolean hasCategory = category != null;
        boolean hasBrands = brands != null && !brands.isEmpty();

        Page<Product> products;

        if (hasCategory && hasBrands) {
            products = productRepository.findByCategoryInAndBrandIn(List.of(category), brands, pageable);

        } else if (hasCategory) {
            products = productRepository.findByCategoryIn(List.of(category), pageable);

        } else if (hasBrands) {
            products = productRepository.findByBrandIn(brands, pageable);

        } else {
            products = productRepository.findAll(pageable);
        }

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

//    public Page<ProductResponseDto> getProductsByCategory(Category category, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Product> products = productRepository.findByCategory(category, pageable);
//
//        return products.map(ProductMapper::toResponseDto);
//    }

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
        Sort springSort = getSort(searchDto.getSortOption());
        Page<Product> products;
        if (searchDto.getBrands() == null) searchDto.setBrands(new ArrayList<>());

        List<Category> matchingCategories = Category.findBySearchQuery(searchDto.getQuery());
        Optional<Brand> matchingBrand = Brand.findBySearchQuery(searchDto.getQuery());

        Pageable pageable = PageRequest.of(searchDto.getPage(), 12, springSort);

        boolean hasCategory = !matchingCategories.isEmpty();
        boolean hasBrands = !searchDto.getBrands().isEmpty() || matchingBrand.isPresent();

        if (hasCategory && hasBrands) {
            List<Brand> brandsCopy = new ArrayList<>(searchDto.getBrands());
            if (matchingBrand.isPresent()) brandsCopy.add(matchingBrand.get());

            products = productRepository.findByCategoryInAndBrandIn(
                    matchingCategories,
                    brandsCopy,
                    pageable
            );
        }

        else if (hasCategory)
            products = productRepository.findByCategoryIn(matchingCategories, pageable);

        else if (hasBrands)
            products = productRepository.findByBrandIn(searchDto.getBrands(), pageable);

        else
            products = productRepository.findByNameContainingIgnoreCase(searchDto.getQuery(), pageable);

        return products.map(ProductMapper::toResponseDto);
    }

    private Sort getSort(SortOption sortOption) {
        if (sortOption == null) return Sort.unsorted();

        return switch (sortOption) {
            case PRICE_LOW_TO_HIGH -> Sort.by("price").ascending();
            case PRICE_HIGH_TO_LOW -> Sort.by("price").descending();
        };
    }
}
