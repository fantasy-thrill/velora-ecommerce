package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.CardType;
import com.example.velora_ecommerce.validation.FutureOrPast;
import com.example.velora_ecommerce.validation.PaymentCardExpiration;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@FutureOrPast
public class UpdatePaymentCardDto implements PaymentCardExpiration {
    private Long id;

    @NotBlank(message = "Name is required")
    private String cardholderName;

    @NotNull(message = "Card type is required")
    private CardType cardType;

    @NotNull(message = "Expiration month is required")
    private Integer expirationMonth;

    @NotNull(message = "Expiration year is required")
    private Integer expirationYear;

    @NotBlank(message = "CVV is required")
    private String cvv;
}
