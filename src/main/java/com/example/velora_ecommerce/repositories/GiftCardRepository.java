package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {
    Optional<GiftCard> findGiftCardByCode(String code);
}
