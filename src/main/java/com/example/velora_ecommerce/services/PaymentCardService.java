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

    PaymentCardRepository paymentCardRepository;

    public PaymentCardService(PaymentCardRepository paymentCardRepository) {
        this.paymentCardRepository = paymentCardRepository;
    }

    public List<PaymentCard> getPaymentCardsByCustomer(Customer customer) {
         return paymentCardRepository.findAllByCustomer(customer);
    }

    public PaymentCard addPaymentCard(Customer customer, PaymentCardDto dto) {
        PaymentCard paymentCard = new PaymentCard();

        paymentCard.setCardType(dto.getCardType());
        paymentCard.setCardProcessor(dto.getCardProcessor());
        paymentCard.setLastFourDigits(dto.getCardNumber()
                .substring(dto.getCardNumber().length() - 4)
        );
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

        return paymentCardRepository.save(paymentCard);
    }

    public void deletePaymentCard(Long id) {
        paymentCardRepository.deleteById(id);
    }
}
