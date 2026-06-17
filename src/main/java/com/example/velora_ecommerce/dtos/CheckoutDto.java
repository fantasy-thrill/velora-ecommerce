package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutDto {

    private Long paymentMethodId;

    @NotBlank(message = "Name is required")
    private String cardholderName;

    private CardType cardType;

    private CardProcessor cardProcessor;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Expiration date is required")
    private String expirationDate;

    @NotBlank(message = "CVV is required")
    private String cvv;

    private ShippingSpeed shippingSpeed;
}
