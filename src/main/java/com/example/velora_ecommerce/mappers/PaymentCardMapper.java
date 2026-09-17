package com.example.velora_ecommerce.mappers;

import com.example.velora_ecommerce.dtos.OrderPaymentCardDto;
import com.example.velora_ecommerce.dtos.PaymentCardResponseDto;
import com.example.velora_ecommerce.entities.PaymentCard;
import com.example.velora_ecommerce.entities.PaymentCardSnapshot;
import org.springframework.stereotype.Component;

@Component
public class PaymentCardMapper {
    public static PaymentCardResponseDto toResponseDto(PaymentCard card) {
        PaymentCardResponseDto cardDto = new PaymentCardResponseDto();

        cardDto.setPaymentCardId(card.getId());
        cardDto.setCardholderName(card.getCardholderName());
        cardDto.setCardType(card.getCardType());
        cardDto.setCardProcessor(card.getCardProcessor());
        cardDto.setLastFourDigits(card.getLastFourDigits());
        cardDto.setExpirationDate(card.getExpirationDate());
        cardDto.setDisplayString(card.displayString());

        return cardDto;
    }

    public static OrderPaymentCardDto toOrderCardDto(PaymentCardSnapshot cardSnapshot) {
        OrderPaymentCardDto snapshotDto = new OrderPaymentCardDto();

        snapshotDto.setCardInfo(cardSnapshot.displayPaymentCardInfo());
        snapshotDto.setProcessorLogo(cardSnapshot.getCardProcessor().getLogoUrl());

        return snapshotDto;
    }

}
