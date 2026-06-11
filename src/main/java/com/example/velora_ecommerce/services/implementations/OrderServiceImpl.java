package com.example.velora_ecommerce.services.implementations;

import com.example.velora_ecommerce.dtos.CheckoutDto;
import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.enums.OrderStatus;
import com.example.velora_ecommerce.repositories.OrderRepository;
import com.example.velora_ecommerce.repositories.PaymentMethodRepository;
import com.example.velora_ecommerce.services.CartService;
import com.example.velora_ecommerce.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final CartService cartService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            PaymentMethodRepository paymentMethodRepository,
            CartService cartService
    ) {
        this.orderRepository = orderRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.cartService = cartService;
    }

    @Override
    public Order placeOrder(Customer customer, CheckoutDto dto) {
        Order order = new Order();
        List<CartItem> cartItems = customer.getCart().getItems();
        BigDecimal totalPrice = cartService.calculateCheckoutTotal(customer.getCart());

        if (dto.getPaymentMethodId() != null) {
            PaymentMethod paymentMethod = paymentMethodRepository
                    .findById(dto.getPaymentMethodId())
                    .orElseThrow(() -> new EntityNotFoundException("Payment method not found"));

            if (!paymentMethod.getCustomer().getId()
                    .equals(customer.getId()))
                throw new IllegalStateException("Payment method does not belong to customer");

            order.setPaymentMethod(paymentMethod);

        } else {
            PaymentMethod paymentMethod = new PaymentMethod();

            paymentMethod.setCardType(dto.getCardType());
            paymentMethod.setLastFourDigits(dto.getCardNumber()
                    .substring(dto.getCardNumber().length() - 4)
            );
            paymentMethod.setCustomer(customer);

            paymentMethodRepository.save(paymentMethod);
            order.setPaymentMethod(paymentMethod);
        }

        order.setCustomer(customer);
        order.setTotalPrice(totalPrice);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPurchasePrice(item.getProduct().getPrice());

            order.getItems().add(orderItem);
        }

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
