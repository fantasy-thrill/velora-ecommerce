package com.example.velora_ecommerce.enums;

import java.math.BigDecimal;

public enum Category {

    COMPUTERS("Computers", new BigDecimal("19.99")),
    LAPTOPS("Laptops", new BigDecimal("14.99")),
    PHONES("Phones", new BigDecimal("7.99")),
    GAMING("Gaming", new BigDecimal("12.99")),
    ACCESSORIES("Accessories", new BigDecimal("4.99"));

    private final String displayName;
    private final BigDecimal shippingCost;

    Category(String displayName, BigDecimal shippingCost) {
        this.displayName = displayName;
        this.shippingCost = shippingCost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }
}
