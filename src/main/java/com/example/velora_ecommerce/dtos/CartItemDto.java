package com.example.velora_ecommerce.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long productId;

    private String productName;

    private String imageUrl;

    private BigDecimal unitPrice;

    private int quantity;

    private BigDecimal lineTotal;
}
