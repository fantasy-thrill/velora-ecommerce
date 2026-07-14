package com.example.velora_ecommerce.enums;

import java.math.BigDecimal;

public enum ShippingSpeed {
    STANDARD(new BigDecimal("3.99")),
    EXPRESS(new BigDecimal("9.99"));

    private final BigDecimal shippingCost;

    ShippingSpeed(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }
}
