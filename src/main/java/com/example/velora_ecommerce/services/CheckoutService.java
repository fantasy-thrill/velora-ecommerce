package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.mappers.*;
import com.example.velora_ecommerce.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutService {
    private final CustomerService customerService;
    private final GiftCardService giftCardService;
    private final OrderRepository orderRepository;

    public CheckoutService(
            CustomerService customerService,
            GiftCardService giftCardService,
            OrderRepository orderRepository
    ) {
        this.customerService = customerService;
        this.giftCardService = giftCardService;
        this.orderRepository = orderRepository;
    }

    public CheckoutPageDto getCheckoutPage(String customerEmail) {
        Customer customer = customerService.getCustomerByEmail(customerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        CheckoutPageDto pageDto = new CheckoutPageDto();
        CustomerProfileDto customerProfileDto = CustomerMapper.toProfileDto(customer);

        Cart customerCart = customer.getCart();

        List<CartItemDto> dtoItems = customerCart.getItems().stream()
                .map(CartItemMapper::toResponseDto)
                .toList();

        List<PaymentCardResponseDto> dtoCards = customer.getPaymentCards().stream()
                .map(PaymentCardMapper::toResponseDto)
                .toList();

        pageDto.setItems(dtoItems);
        pageDto.setAddress(customerProfileDto.getAddress());
        pageDto.setPaymentCards(dtoCards);
        pageDto.setSelectedPaymentMethod(dtoCards.getFirst());

        return pageDto;
    }

    public CheckoutSummaryDto buildCheckoutSummary(String customerEmail, CheckoutDto checkoutDto) {
        Customer customer = customerService.getCustomerByEmail(customerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Cart customerCart = customer.getCart();

        BigDecimal total = customerCart.calculateSubtotal();
        BigDecimal shippingCost = checkoutDto.getShippingSpeed().getShippingCost();
        total = total.add(shippingCost);

        CheckoutSummaryDto summaryDto = new CheckoutSummaryDto();

        if (checkoutDto.getGiftCardCode() != null) {
            GiftCard giftCard = giftCardService.getGiftCardByCode(checkoutDto.getGiftCardCode())
                    .orElseThrow(() -> new EntityNotFoundException("Gift card not found"));

            total = total.subtract(giftCard.getBalance());
            summaryDto.setGiftCardAmount(giftCard.getBalance());
        }

        summaryDto.setSubtotal(customerCart.calculateSubtotal());
        summaryDto.setShipping(shippingCost);
        summaryDto.setTotal(total);

        return summaryDto;
    }

    public String displayConfirmation(String customerEmail, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!order.getCustomer().getEmail().equals(customerEmail))
            throw new IllegalStateException("Order does not belong to customer");

        return "Thank you for your order. Your order ID number is #" + order.getId();
    }
}
