package com.example.velora_ecommerce.mappers;

import com.example.velora_ecommerce.dtos.GiftCardResponseDto;
import com.example.velora_ecommerce.entities.GiftCard;
import org.springframework.stereotype.Component;

@Component
public class GiftCardMapper {
    public static GiftCardResponseDto toResponseDto(GiftCard giftCard) {
        GiftCardResponseDto giftCardDto = new GiftCardResponseDto();

        giftCardDto.setCode(giftCard.getCode());
        giftCardDto.setBalance(giftCard.getBalance());
        giftCardDto.setDisplayString(giftCard.displayString());

        return giftCardDto;
    }
}
