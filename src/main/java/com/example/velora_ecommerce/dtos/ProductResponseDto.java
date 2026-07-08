package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.Brand;
import com.example.velora_ecommerce.enums.Category;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;

    private String name;

    private Category category;

    private BigDecimal price;

    private String description;

    private Brand brand;

    private String imageUrl;

    private int stockQuantity;

    private Map<String, String> specifications;
}
