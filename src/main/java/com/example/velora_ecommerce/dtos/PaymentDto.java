package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.CardType;

public interface PaymentDto {
    CardType getCardType();

    String getCardNumber();
}
