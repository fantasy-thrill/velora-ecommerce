package com.example.velora_ecommerce.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class GiftCardResponseDto {
    private String code;

    private BigDecimal balance;

    private String displayString;
}
