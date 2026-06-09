package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.Order;
import com.example.velora_ecommerce.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);

    List<Order> getOrdersByCustomerAndDateRange(
            Customer customer,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    List<Order> getOrdersByCustomerAndStatus(Customer customer, OrderStatus status);

    void cancelOrder(Long id);
}
