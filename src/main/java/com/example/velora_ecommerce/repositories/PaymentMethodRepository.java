package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository <PaymentMethod, Long> {
    List<PaymentMethod> findAllByCustomer(Customer customer);
}
