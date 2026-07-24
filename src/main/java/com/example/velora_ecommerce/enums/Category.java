package com.example.velora_ecommerce.enums;

public enum Category {

    DESKTOPS("Desktop PCs"),
    LAPTOPS("Laptops"),
    MONITORS("Monitors"),
    KEYBOARDS("Keyboards"),
    MICE("Mice"),
    TABLETS("Tablets"),
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
