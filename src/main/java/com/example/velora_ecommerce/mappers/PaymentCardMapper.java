package com.example.velora_ecommerce.mappers;

import com.example.velora_ecommerce.dtos.PaymentCardResponseDto;
import com.example.velora_ecommerce.entities.PaymentCard;
import org.springframework.stereotype.Component;

@Component
public class PaymentCardMapper {
    public static PaymentCardResponseDto toResponseDto(PaymentCard card) {
        PaymentCardResponseDto cardDto = new PaymentCardResponseDto();

        cardDto.setPaymentCardId(card.getId());
        cardDto.setCardProcessor(card.getCardProcessor());
        cardDto.setLastFourDigits(card.getLastFourDigits());
        cardDto.setExpirationDate(card.getExpirationDate());
        cardDto.setDisplayString(card.displayString());

        return cardDto;
    }
}
