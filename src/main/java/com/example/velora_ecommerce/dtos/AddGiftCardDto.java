package com.example.velora_ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AddGiftCardDto {
    @NotBlank(message = "Please enter a gift card code")
    private String code;
}
