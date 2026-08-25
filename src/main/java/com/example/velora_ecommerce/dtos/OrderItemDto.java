package com.example.velora_ecommerce.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;

    private ProductResponseDto product;

    private int quantity;

    private BigDecimal purchasePrice;

    private LocalDate deliveryDate;
}
