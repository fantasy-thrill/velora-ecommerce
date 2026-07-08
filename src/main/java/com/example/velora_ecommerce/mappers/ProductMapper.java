package com.example.velora_ecommerce.mappers;

import com.example.velora_ecommerce.dtos.ProductResponseDto;
import com.example.velora_ecommerce.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductResponseDto toResponseDto(Product product) {

        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setStockQuantity(product.getStockQuantity());

        return dto;
    }

}
