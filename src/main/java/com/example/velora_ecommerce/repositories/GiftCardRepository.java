package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {
    List<GiftCard> findAllGiftCardsByCustomer(Customer customer);
}
