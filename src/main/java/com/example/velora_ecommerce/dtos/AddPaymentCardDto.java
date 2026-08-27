package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.CardProcessor;
import com.example.velora_ecommerce.enums.CardType;
import com.example.velora_ecommerce.validation.FutureOrPast;
import com.example.velora_ecommerce.validation.PaymentCardExpiration;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@FutureOrPast
public class AddPaymentCardDto implements PaymentCardExpiration {
    @NotBlank(message = "Name is required")
    private String cardholderName;

    @NotNull
    private CardProcessor cardProcessor;

    @NotNull
    private CardType cardType;

    @NotBlank(message = "Card number is required")
    @Size(min = 16, message = "Card number must be at least 16 digits long")
    private String cardNumber;

    @NotNull(message = "Expiration month is required")
    private Integer expirationMonth;

    @NotNull(message = "Expiration year is required")
    private Integer expirationYear;

    @NotBlank(message = "CVV is required")
    private String cvv;
}
