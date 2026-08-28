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
        Customer customer = getCustomer(customerEmail);
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

        return pageDto;
    }

    public CheckoutSummaryDto buildCheckoutSummary(String customerEmail, CheckoutDto checkoutDto) {
        Customer customer = getCustomer(customerEmail);
        Cart customerCart = customer.getCart();
        CheckoutSummaryDto summaryDto = new CheckoutSummaryDto();

        BigDecimal total = customerCart.calculateSubtotal();

        if (checkoutDto.getShippingSpeed() != null) {
            BigDecimal shippingCost = checkoutDto.getShippingSpeed().getShippingCost();
            total = total.add(shippingCost);
            summaryDto.setShipping(shippingCost);
        }

        if (checkoutDto.getGiftCardCode() != null) {
            GiftCard giftCard = giftCardService.getGiftCardByCode(checkoutDto.getGiftCardCode());

            total = total.subtract(giftCard.getBalance());
            if (total.compareTo(BigDecimal.ZERO) < 0) total = BigDecimal.ZERO;

            summaryDto.setGiftCard(GiftCardMapper.toResponseDto(giftCard));
        }

        summaryDto.setSubtotal(customerCart.calculateSubtotal());
        summaryDto.setTotal(total);

        return summaryDto;
    }

    public String displayConfirmation(String customerEmail, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!order.getCustomer().getEmail().equals(customerEmail))
            throw new IllegalStateException("Order does not belong to customer");

        return "Thank you for your order. Your confirmation number is #" + order.getId();
    }

    private Customer getCustomer(String email) {
        return customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
