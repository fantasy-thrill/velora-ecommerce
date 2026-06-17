package com.example.velora_ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class GiftCardDto {
    @NotBlank
    private BigDecimal balance;

    @NotBlank
    private String code;
}
