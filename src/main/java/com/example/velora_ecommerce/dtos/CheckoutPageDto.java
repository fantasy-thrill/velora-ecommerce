package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.ShippingSpeed;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutPageDto {
    private List<CartItemDto> items;

    private AddressDto address;

    private List<PaymentCardResponseDto> paymentCards;

    private PaymentCardResponseDto selectedPaymentMethod;

    private ShippingSpeed shippingSpeed = ShippingSpeed.STANDARD;

//    private CheckoutSummaryDto summary;
}
