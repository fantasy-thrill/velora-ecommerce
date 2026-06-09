package com.example.velora_ecommerce.services.implementations;

import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.Order;
import com.example.velora_ecommerce.enums.OrderStatus;
import com.example.velora_ecommerce.repositories.OrderRepository;
import com.example.velora_ecommerce.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByCustomerAndDateRange(
            Customer customer,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return orderRepository.findByCustomerAndOrderDateBetween(customer, startDate, endDate);
    }

    @Override
    public List<Order> getOrdersByCustomerAndStatus(Customer customer, OrderStatus status) {
        return orderRepository.findByCustomerAndStatus(customer, status);
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        LocalDateTime currentDateAndTime = LocalDateTime.now();

        if (currentDateAndTime.isAfter(order.getDate().plusHours(12))) {
            throw new IllegalStateException(
                    "Order has been placed more than 12 hours ago. Therefore, it cannot be canceled."
            );
        } else {
            order.setStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
        }
    }
}
