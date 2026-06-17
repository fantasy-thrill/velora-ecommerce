package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    List<PaymentCard> findAllByCustomer(Customer customer);
}
