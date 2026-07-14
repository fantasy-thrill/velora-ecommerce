package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.GiftCardDto;
import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.GiftCard;
import com.example.velora_ecommerce.repositories.GiftCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCardService {
    private final GiftCardRepository giftCardRepository;

    public GiftCardService(GiftCardRepository giftCardRepository) {
        this.giftCardRepository = giftCardRepository;
    }

    public List<GiftCard> getGiftCardsForCustomer(Customer customer) {
        return giftCardRepository.findAllGiftCardsByCustomer(customer);
    }

    public Optional<GiftCard> getGiftCardByCode(String code) {
        return giftCardRepository.findGiftCardByCode(code);
    }

    public GiftCard addGiftCard(Customer customer, GiftCardDto dto) {
        GiftCard giftCard = new GiftCard();
        giftCard.setBalance(dto.getBalance());
        giftCard.setCode(dto.getCode());
        giftCard.setCustomer(customer);

        return giftCardRepository.save(giftCard);
    }
}
