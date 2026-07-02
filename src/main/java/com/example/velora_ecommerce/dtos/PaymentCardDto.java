package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.CardProcessor;
import com.example.velora_ecommerce.enums.CardType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PaymentCardDto {
    @NotBlank(message = "Name is required")
    private String cardholderName;

    @NotNull
    private CardProcessor cardProcessor;

    @NotNull
    private CardType cardType;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Expiration date is required")
    private LocalDate expirationDate;

    @NotBlank(message = "CVV is required")
    private String cvv;
}
