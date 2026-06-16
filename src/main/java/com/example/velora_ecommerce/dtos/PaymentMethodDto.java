package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.CardType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PaymentMethodDto implements PaymentDto {
    @NotBlank(message = "Name is required")
    private String cardholderName;

    @NotNull
    private CardType cardType;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Expiration date is required")
    private String expirationDate;

    @NotBlank(message = "CVV is required")
    private String cvv;
}
