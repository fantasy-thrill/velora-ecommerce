package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.Brand;
import com.example.velora_ecommerce.enums.Category;
import com.example.velora_ecommerce.enums.SortOption;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDto {
    private String query;

    private Category category;

    private List<Brand> brands = new ArrayList<>();

    private SortOption sortOption;

    private int page = 0;
}
