package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.ProductFilterDto;
import com.example.velora_ecommerce.enums.Brand;
import com.example.velora_ecommerce.enums.Category;
import com.example.velora_ecommerce.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.velora_ecommerce.dtos.ProductResponseDto;
import com.example.velora_ecommerce.entities.Product;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldFetchAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(new BigDecimal(999.99));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Keyboard");
        product2.setPrice(new BigDecimal(79.99));

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("Gaming Mouse");
        product3.setPrice(new BigDecimal(49.99));

        Product product4 = new Product();
        product4.setId(4L);
        product4.setName("27-inch Monitor");
        product4.setPrice(new BigDecimal(299.99));

        Product product5 = new Product();
        product5.setId(5L);
        product5.setName("Desktop Computer");
        product5.setPrice(new BigDecimal(1299.99));

        Product product6 = new Product();
        product6.setId(6L);
        product6.setName("Wireless Headset");
        product6.setPrice(new BigDecimal(119.99));

        Product product7 = new Product();
        product7.setId(7L);
        product7.setName("Webcam");
        product7.setPrice(new BigDecimal(69.99));

        List<Product> productList = List.of(
                product1,
                product2,
                product3,
                product4,
                product5,
                product6,
                product7
        );

        Page<Product> productPage = new PageImpl<>(productList);
        Pageable pageable = PageRequest.of(0, 12);
        when(productRepository.findAll(pageable))
                .thenReturn(productPage);

        ProductFilterDto filterDto = new ProductFilterDto();

        // Act
        Page<ProductResponseDto> result = productService.getAllProducts(filterDto);

        // Assert
        assertEquals(7, result.getContent().size());
        assertEquals("Laptop", result.getContent().get(0).getName());
        assertEquals("Keyboard", result.getContent().get(1).getName());
        assertEquals("Gaming Mouse", result.getContent().get(2).getName());
        assertEquals("27-inch Monitor", result.getContent().get(3).getName());
        assertEquals("Desktop Computer", result.getContent().get(4).getName());
        assertEquals("Wireless Headset", result.getContent().get(5).getName());
        assertEquals("Webcam", result.getContent().get(6).getName());

        verify(productRepository).findAll(pageable);
    }

    @Test
    void shouldFetchProductById() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldSearchProductsWithoutBrands() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Gaming Laptop");
        product1.setPrice(new BigDecimal(1299.99));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Business Laptop");
        product2.setPrice(new BigDecimal(899.99));

        ProductFilterDto searchDto = new ProductFilterDto();
        searchDto.setQuery("gaming");
        searchDto.setPage(0);
        searchDto.setBrands(Collections.emptyList());

        Pageable pageable = PageRequest.of(0, 12, Sort.unsorted());
        Page<Product> productPage = new PageImpl<>(List.of(product1));

        when(productRepository.findByNameContainingIgnoreCase("gaming", pageable))
                .thenReturn(productPage);

        // Act
        Page<ProductResponseDto> result = productService.searchProducts(searchDto);

        // Assert
        assertEquals(1, result.getContent().size());
        assertEquals("Gaming Laptop", result.getContent().get(0).getName());
//        assertEquals("Business Laptop", result.getContent().get(1).getName());

        verify(productRepository).findByNameContainingIgnoreCase("gaming", pageable);

        verify(productRepository, never()).findByNameContainingIgnoreCaseAndBrandIn(
                        anyString(),
                        anyList(),
                        any(Pageable.class)
                );
    }

    @Test
    void shouldSearchProductsBasedOnBrandAndCategoryIfPresent() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setBrand(Brand.VERTEX);
        product1.setPrice(new BigDecimal("999.99"));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Keyboard");
        product2.setBrand(Brand.VERTEX);
        product2.setPrice(new BigDecimal("79.99"));

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("Gaming Mouse");
        product3.setBrand(Brand.IRONCORE);
        product3.setPrice(new BigDecimal("49.99"));

        Product product4 = new Product();
        product4.setId(4L);
        product4.setName("27-inch Monitor");
        product4.setBrand(Brand.NIMBUS);
        product4.setPrice(new BigDecimal("299.99"));

        Product product5 = new Product();
        product5.setId(5L);
        product5.setName("Desktop Computer");
        product5.setBrand(Brand.VERTEX);
        product5.setPrice(new BigDecimal("1299.99"));

        Product product6 = new Product();
        product6.setId(6L);
        product6.setName("Wireless Headset");
        product6.setBrand(Brand.RESONA);
        product6.setPrice(new BigDecimal("119.99"));

        Product product7 = new Product();
        product7.setId(7L);
        product7.setName("Webcam");
        product7.setBrand(Brand.GALAGEAR);
        product7.setPrice(new BigDecimal("69.99"));

        List<Product> productList = List.of(
                product1,
                product2,
                product3,
                product4,
                product5,
                product6,
                product7
        );
    }
}
