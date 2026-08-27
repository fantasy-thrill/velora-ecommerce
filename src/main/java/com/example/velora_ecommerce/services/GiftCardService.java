package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.entities.GiftCard;
import com.example.velora_ecommerce.repositories.GiftCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GiftCardService {
    private final GiftCardRepository giftCardRepository;

    public GiftCardService(GiftCardRepository giftCardRepository) {
        this.giftCardRepository = giftCardRepository;
    }

    public GiftCard getGiftCardByCode(String code) {
        GiftCard giftCard = giftCardRepository.findGiftCardByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Gift card not found"));

        return giftCard;
    }

    public boolean giftCardExists(String code) {
        Optional<GiftCard> giftCard = giftCardRepository.findGiftCardByCode(code);
        return giftCard.isPresent();
    }
}
