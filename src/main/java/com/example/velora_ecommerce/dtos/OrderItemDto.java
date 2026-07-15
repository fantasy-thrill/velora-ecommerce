package com.example.velora_ecommerce.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;

    private ProductResponseDto product;

    private int quantity;

    private BigDecimal purchasePrice;
}
