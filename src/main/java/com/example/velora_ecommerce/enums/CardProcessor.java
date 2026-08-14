package com.example.velora_ecommerce.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardProcessor {
    VISA("Visa"),
    MASTERCARD("MasterCard"),
    AMEX("American Express"),
    DISCOVER("Discover");

    private final String displayString;
}
