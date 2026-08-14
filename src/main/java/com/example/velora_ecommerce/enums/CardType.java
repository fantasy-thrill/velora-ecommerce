package com.example.velora_ecommerce.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardType {
    CREDIT("Credit"),
    DEBIT("Debit"),
    GIFT_CARD("Gift card");

    private final String displayString;
}
