package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.CardProcessor;
import com.example.velora_ecommerce.enums.CardType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePaymentCardDto {
    private Long id;

    @NotBlank(message = "Name is required")
    private String cardholderName;

    @NotNull(message = "Card processor is required")
    private CardProcessor cardProcessor;

    @NotNull(message = "Card type is required")
    private CardType cardType;

    private String cardNumber;

    @NotNull(message = "Card must have an expiration date")
    private LocalDate expirationDate;

    private String cvv;
}
