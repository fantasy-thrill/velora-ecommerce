package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.entities.Address;
import com.example.velora_ecommerce.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutDto {

    private Long paymentMethodId;

    @NotNull
    @Valid
    private Address address;

    @NotBlank(message = "Name is required")
    private String cardholderName;

    private CardType cardType;

    @NotNull
    private CardProcessor cardProcessor;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Expiration date is required")
    private String expirationDate;

    @NotBlank(message = "CVV is required")
    private String cvv;

    @NotNull
    private ShippingSpeed shippingSpeed;
}
