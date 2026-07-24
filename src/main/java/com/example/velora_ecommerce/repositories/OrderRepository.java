package com.example.velora_ecommerce.repositories;

import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.Order;
import com.example.velora_ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    List<Order> findByCustomer(Customer customer);

    List<Order> findByCustomerAndDateBetween(Customer customer, LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findByCustomerAndStatus(Customer customer, OrderStatus status);
}
