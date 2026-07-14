package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.*;
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
    private final CustomerService customerService;

    public OrderService(
            OrderRepository orderRepository,
            PaymentCardRepository paymentCardRepository,
            PaymentCardService paymentCardService,
            CustomerService customerService
    ) {
        this.orderRepository = orderRepository;
        this.paymentCardRepository = paymentCardRepository;
        this.paymentCardService = paymentCardService;
        this.customerService = customerService;
    }

    public Order placeOrder(String customerEmail, CheckoutDto checkoutDto, CheckoutSummaryDto summaryDto) {
        Customer customer = customerService.getCustomerByEmail(customerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Order order = new Order();
        Address address = new Address();
        AddressDto dtoAddress = checkoutDto.getAddress();
        List<CartItem> cartItems = customer.getCart().getItems();
        BigDecimal totalPrice = summaryDto.getTotal();

        if (checkoutDto.getPaymentMethodId() != null) {
            PaymentCard paymentCard = paymentCardRepository
                    .findById(checkoutDto.getPaymentMethodId())
                    .orElseThrow(() -> new EntityNotFoundException("Credit or debit card not found"));

            if (!paymentCard.getCustomer().getId()
                    .equals(customer.getId()))
                throw new IllegalStateException("Credit or debit card does not belong to customer");

            order.setPaymentCard(paymentCard);

        } // else {
//            PaymentCardDto cardDto = new PaymentCardDto();
//
//            cardDto.setCardType(checkoutDto.getCardType());
//            cardDto.setCardProcessor(checkoutDto.getCardProcessor());
//            cardDto.setCardNumber(checkoutDto.getCardNumber());
//
//            PaymentCard paymentCard = paymentCardService.addPaymentCard(customer, cardDto);
//            order.setPaymentCard(paymentCard);
//        }

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
