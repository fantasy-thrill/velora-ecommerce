package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.PaymentCardDto;
import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.PaymentCard;
import com.example.velora_ecommerce.repositories.PaymentCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;
    private final CustomerService customerService;

    public PaymentCardService(PaymentCardRepository paymentCardRepository, CustomerService customerService) {
        this.paymentCardRepository = paymentCardRepository;
        this.customerService = customerService;
    }

    public List<PaymentCard> getPaymentCardsByCustomer(String email) {
        Customer customer = customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        return paymentCardRepository.findAllByCustomer(customer);
    }

    public PaymentCard addPaymentCard(String customerEmail, PaymentCardDto dto) {
        Customer customer = customerService.getCustomerByEmail(customerEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        PaymentCard paymentCard = new PaymentCard();

        paymentCard.setCardType(dto.getCardType());
        paymentCard.setCardProcessor(dto.getCardProcessor());
        paymentCard.setLastFourDigits(dto.getCardNumber()
                .substring(dto.getCardNumber().length() - 4)
        );
        paymentCard.setExpirationDate(dto.getExpirationDate());
        paymentCard.setCustomer(customer);

        return paymentCardRepository.save(paymentCard);
    }

    public void updatePaymentCard(String email, Long id, PaymentCardDto dto) {
        Customer customer = customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        List<PaymentCard> customerPaymentCards = customer.getPaymentCards();

        PaymentCard cardToUpdate = customerPaymentCards.stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Credit or debit card not found for customer"));

        if (!cardToUpdate.getCustomer().getId().equals(customer.getId()))
            throw new IllegalStateException("Credit or debit card does not belong to customer");

        cardToUpdate.setCardProcessor(dto.getCardProcessor());
        cardToUpdate.setCardType(dto.getCardType());
        cardToUpdate.setLastFourDigits(dto.getCardNumber()
                .substring(dto.getCardNumber().length() - 4)
        );
        cardToUpdate.setExpirationDate(dto.getExpirationDate());

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
