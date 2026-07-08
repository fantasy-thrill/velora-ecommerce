package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.enums.Brand;
import com.example.velora_ecommerce.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Brand brand;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private String imageUrl;

    @ElementCollection
    @CollectionTable(
            name = "product_specifications",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @MapKeyColumn(name = "spec_name")
    @Column(name = "spec_value")
    private Map<String, String> specifications = new HashMap<>();
}
