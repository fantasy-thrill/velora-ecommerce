package com.example.velora_ecommerce.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutSummaryDto {
    private BigDecimal subtotal;

    private BigDecimal shipping;

    private BigDecimal discount;

    private BigDecimal giftCardAmount;

    private BigDecimal total;
}
