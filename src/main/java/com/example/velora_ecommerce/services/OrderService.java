package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.enums.OrderStatus;
import com.example.velora_ecommerce.mappers.OrderMapper;
import com.example.velora_ecommerce.repositories.OrderRepository;
import com.example.velora_ecommerce.repositories.PaymentCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentCardRepository paymentCardRepository;
    private final CustomerService customerService;
    private final GiftCardService giftCardService;

    public OrderService(
            OrderRepository orderRepository,
            PaymentCardRepository paymentCardRepository,
            CustomerService customerService,
            GiftCardService giftCardService
    ) {
        this.orderRepository = orderRepository;
        this.paymentCardRepository = paymentCardRepository;
        this.customerService = customerService;
        this.giftCardService = giftCardService;
    }

    @Transactional
    public Order placeOrder(String customerEmail, CheckoutDto checkoutDto, CheckoutSummaryDto summaryDto) {
        Customer customer = customerService.getCustomerByEmail(customerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Order order = new Order();
        Address address = new Address();
        PaymentCardSnapshot cardSnapshot = new PaymentCardSnapshot();
        AddressDto dtoAddress = checkoutDto.getAddress();
        List<CartItem> cartItems = customer.getCart().getItems();
        BigDecimal totalPrice = summaryDto.getTotal();

        if (checkoutDto.getPaymentMethodId() == null)
            throw new NullPointerException("No valid payment method entered");

        PaymentCard paymentCard = paymentCardRepository
                .findById(checkoutDto.getPaymentMethodId())
                .orElseThrow(() -> new EntityNotFoundException("Credit or debit card not found"));

        if (!paymentCard.getCustomer().getId()
                .equals(customer.getId()))
            throw new IllegalStateException("Credit or debit card does not belong to customer");

        cardSnapshot.setCardType(paymentCard.getCardType());
        cardSnapshot.setCardProcessor(paymentCard.getCardProcessor());
        cardSnapshot.setLastFourDigits(paymentCard.getLastFourDigits());

        order.setPaymentCardInfo(cardSnapshot);

        address.setCustomerFirstName(dtoAddress.getCustomerFullName().split(" ")[0]);
        address.setCustomerLastName(dtoAddress.getCustomerFullName().split(" ")[1]);
        address.setStreet(dtoAddress.getStreet());
        address.setCity(dtoAddress.getCity());
        address.setState(dtoAddress.getState());
        address.setZipCode(dtoAddress.getZipCode());

        order.setCustomer(customer);
        order.setShippingAddress(address);
        order.setTotalPrice(totalPrice);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("Subtotal", summaryDto.getSubtotal());
        summary.put("Shipping", summaryDto.getShipping());
        summary.put("Total", summaryDto.getTotal());

        if (summaryDto.getDiscount() != null) summary.put("Discount", summaryDto.getDiscount());

        if (summaryDto.getGiftCard() != null) {
            GiftCard giftCard = giftCardService.getGiftCardByCode(checkoutDto.getGiftCardCode());
            giftCard.setBalance(BigDecimal.ZERO);

            summary.put("Gift card", summaryDto.getGiftCard().getBalance());
        }

        order.setSummary(summary);

        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPurchasePrice(item.getProduct().getPrice());

            order.getItems().add(orderItem);
        }

        customer.getCart().getItems().clear();

        return orderRepository.save(order);
    }

    public OrderSummaryDto getOrderDetails(String customerEmail, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!order.getCustomer().getEmail().equals(customerEmail))
            throw new IllegalStateException("Order does not belong to customer");

        return OrderMapper.toResponseDto(order);
    }

    public List<OrderSummaryDto> getOrdersForCustomer(String email) {
        Customer customer = customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        return orderRepository.findByCustomerOrderByDateDesc(customer).stream()
                .map(OrderMapper::toResponseDto)
                .toList();
    }

    public List<Order> getOrdersByCustomerAndDateRange(
            Customer customer,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return orderRepository.findByCustomerAndDateBetween(customer, startDate, endDate);
    }

    public List<Order> getOrdersByCustomerAndStatus(Customer customer, OrderStatus status) {
        return orderRepository.findByCustomerAndStatus(customer, status);
    }

    public void cancelOrder(String customerEmail, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!order.getCustomer().getEmail().equals(customerEmail))
            throw new IllegalStateException("Order does not belong to customer");

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
