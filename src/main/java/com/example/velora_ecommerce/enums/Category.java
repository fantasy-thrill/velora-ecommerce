package com.example.velora_ecommerce.enums;

import java.math.BigDecimal;

public enum Category {

    COMPUTERS("Computers"),
    LAPTOPS("Laptops"),
    PHONES("Phones"),
    GAMING("Gaming"),
    ACCESSORIES("Accessories");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
