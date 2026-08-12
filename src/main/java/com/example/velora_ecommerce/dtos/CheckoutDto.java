package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutDto {
    private Long paymentMethodId;

    @Valid
    private AddressDto address;

    private String giftCardCode;

    private String couponCode;

    private ShippingSpeed shippingSpeed = ShippingSpeed.STANDARD;
}
