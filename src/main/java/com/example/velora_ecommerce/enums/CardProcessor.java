package com.example.velora_ecommerce.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardProcessor {
    VISA("Visa", "/images/visa_logo.png"),
    MASTERCARD("MasterCard", "/images/mastercard_logo.png"),
    AMEX("American Express", "/images/american-express-logo.png"),
    DISCOVER("Discover", "/images/discover_logo.jpg");

    private final String displayString;
    private final String logoUrl;
}
