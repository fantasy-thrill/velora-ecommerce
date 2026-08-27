package com.example.velora_ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class GiftCardDto {
    @NotBlank(message = "Please enter a gift card code")
    private String code;
}
