package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository <PaymentMethod, Long> {
}
