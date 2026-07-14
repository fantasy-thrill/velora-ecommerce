package com.example.velora_ecommerce.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private List<CartItemDto> items;

    private BigDecimal subtotal;
}
