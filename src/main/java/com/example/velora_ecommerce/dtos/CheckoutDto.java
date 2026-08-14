package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutDto {
    @NotNull(message = "Please select a payment method.")
    private Long paymentMethodId;

    @Valid
    @NotNull(message = "Please select a delivery address.")
    private AddressDto address;

    private String giftCardCode;

    private String couponCode;

    @NotNull(message = "Please select a shipping speed.")
    private ShippingSpeed shippingSpeed;
}
