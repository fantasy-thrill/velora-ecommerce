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

    public List<PaymentCard> getPaymentCardsByCustomer(Customer customer) {
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

    public PaymentCard updatePaymentCard(Long id, PaymentCardDto dto) {
        PaymentCard paymentCard = paymentCardRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Credit or debit card not found"));

        paymentCard.setCardProcessor(dto.getCardProcessor());
        paymentCard.setCardType(dto.getCardType());
        paymentCard.setLastFourDigits(dto.getCardNumber()
                .substring(dto.getCardNumber().length() - 4)
        );
        paymentCard.setExpirationDate(dto.getExpirationDate());

        return paymentCardRepository.save(paymentCard);
    }

    public void deletePaymentCard(Long id) {
        paymentCardRepository.deleteById(id);
    }
}
