package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.PaymentDto;
import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.entities.PaymentMethod;
import com.example.velora_ecommerce.repositories.PaymentMethodRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<PaymentMethod> getPaymentMethodsByCustomer(Customer customer) {
        return paymentMethodRepository.findAllByCustomer(customer);
    }

    public PaymentMethod addPaymentMethod(Customer customer, PaymentDto dto) {
        PaymentMethod paymentMethod = new PaymentMethod();

        paymentMethod.setCardType(dto.getCardType());
        paymentMethod.setLastFourDigits(dto.getCardNumber()
                .substring(dto.getCardNumber().length() - 4)
        );
        paymentMethod.setCustomer(customer);

        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod updatePaymentMethod(Long id, PaymentDto dto) {
        PaymentMethod paymentMethod = paymentMethodRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found"));

        paymentMethod.setCardType(dto.getCardType());
        paymentMethod.setLastFourDigits(dto.getCardNumber()
                .substring(dto.getCardNumber().length() - 4)
        );

        return paymentMethodRepository.save(paymentMethod);
    }

    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}
