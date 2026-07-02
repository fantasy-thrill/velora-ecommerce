package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.CheckoutDto;
import com.example.velora_ecommerce.dtos.PaymentCardDto;
import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.enums.OrderStatus;
import com.example.velora_ecommerce.repositories.OrderRepository;
import com.example.velora_ecommerce.repositories.PaymentCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentCardRepository paymentCardRepository;
    private final PaymentCardService paymentCardService;
    private final CartService cartService;

    public OrderService(
            OrderRepository orderRepository,
            PaymentCardRepository paymentCardRepository, PaymentCardService paymentCardService,
            CartService cartService
    ) {
        this.orderRepository = orderRepository;
        this.paymentCardRepository = paymentCardRepository;
        this.paymentCardService = paymentCardService;
        this.cartService = cartService;
    }

    public Order placeOrder(Customer customer, CheckoutDto dto) {
        Order order = new Order();
        Address address = new Address();
        Address dtoAddress = dto.getAddress();
        List<CartItem> cartItems = customer.getCart().getItems();
        BigDecimal totalPrice = cartService.calculateCheckoutTotal(customer.getCart());

        if (dto.getPaymentMethodId() != null) {
            PaymentCard paymentCard = paymentCardRepository
                    .findById(dto.getPaymentMethodId())
                    .orElseThrow(() -> new EntityNotFoundException("Credit or debit card not found"));

            if (!paymentCard.getCustomer().getId()
                    .equals(customer.getId()))
                throw new IllegalStateException("Credit or debit card does not belong to customer");

            order.setPaymentCard(paymentCard);

        } else {
            PaymentCardDto cardDto = new PaymentCardDto();

            cardDto.setCardType(dto.getCardType());
            cardDto.setCardProcessor(dto.getCardProcessor());
            cardDto.setCardNumber(dto.getCardNumber());

            PaymentCard paymentCard = paymentCardService.addPaymentCard(customer, cardDto);
            order.setPaymentCard(paymentCard);
        }

        address.setFirstName(customer.getFirstName());
        address.setLastName(customer.getLastName());
        address.setStreet(dtoAddress.getStreet());
        address.setCity(dtoAddress.getCity());
        address.setState(dtoAddress.getState());
        address.setZipCode(dtoAddress.getZipCode());

        order.setCustomer(customer);
        order.setShippingAddress(address);
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

    public List<Order> getOrdersByCustomerAndDateRange(
            Customer customer,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return orderRepository.findByCustomerAndOrderDateBetween(customer, startDate, endDate);
    }

    public List<Order> getOrdersByCustomerAndStatus(Customer customer, OrderStatus status) {
        return orderRepository.findByCustomerAndStatus(customer, status);
    }

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
