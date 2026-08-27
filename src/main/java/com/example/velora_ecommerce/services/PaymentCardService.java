package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.PaymentCard;
import com.example.velora_ecommerce.mappers.PaymentCardMapper;
import com.example.velora_ecommerce.repositories.PaymentCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class PaymentCardService {
    private final PaymentCardRepository paymentCardRepository;
    private final CustomerService customerService;

    public PaymentCardService(PaymentCardRepository paymentCardRepository, CustomerService customerService) {
        this.paymentCardRepository = paymentCardRepository;
        this.customerService = customerService;
    }

    public List<PaymentCardResponseDto> getPaymentCardsByCustomer(String email) {
        Customer customer = customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        return paymentCardRepository.findAllByCustomer(customer).stream()
                .map(PaymentCardMapper::toResponseDto)
                .toList();
    }

    public PaymentCard addPaymentCard(String customerEmail, AddPaymentCardDto dto) {
        Customer customer = customerService.getCustomerByEmail(customerEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        PaymentCard paymentCard = new PaymentCard();

        paymentCard.setCardholderName(dto.getCardholderName());
        paymentCard.setCardType(dto.getCardType());
        paymentCard.setCardProcessor(dto.getCardProcessor());
        paymentCard.setLastFourDigits(dto.getCardNumber().substring(dto.getCardNumber().length() - 4));

        int month = dto.getExpirationMonth();
        int year = dto.getExpirationYear();

        paymentCard.setExpirationDate(YearMonth.of(year, month).atEndOfMonth());
        paymentCard.setCustomer(customer);

        return paymentCardRepository.save(paymentCard);
    }

    public void updatePaymentCard(String email, Long id, UpdatePaymentCardDto dto) {
        Customer customer = customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        List<PaymentCard> customerPaymentCards = customer.getPaymentCards();

        PaymentCard cardToUpdate = customerPaymentCards.stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Credit or debit card not found for customer"));

        if (!cardToUpdate.getCustomer().getId().equals(customer.getId()))
            throw new IllegalStateException("Credit or debit card does not belong to customer");

        cardToUpdate.setCardholderName(dto.getCardholderName());
        cardToUpdate.setCardType(dto.getCardType());

        int month = dto.getExpirationMonth();
        int year = dto.getExpirationYear();

        cardToUpdate.setExpirationDate(YearMonth.of(year, month).atEndOfMonth());

        paymentCardRepository.save(cardToUpdate);
    }

    public void deletePaymentCard(String email, Long id) {
        Customer customer = customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        List<PaymentCard> customerPaymentCards = customer.getPaymentCards();

        PaymentCard cardToDelete = customerPaymentCards.stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Credit or debit card not found for customer"));

        if (!cardToDelete.getCustomer().getId().equals(customer.getId()))
            throw new IllegalStateException("Credit or debit card does not belong to customer");

        paymentCardRepository.deleteById(id);
    }
}
