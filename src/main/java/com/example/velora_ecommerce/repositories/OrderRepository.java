package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
