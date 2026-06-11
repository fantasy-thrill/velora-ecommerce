package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.ChangePasswordDto;
import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import com.example.velora_ecommerce.dtos.CustomerUpdateDto;
import com.example.velora_ecommerce.entities.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getCustomerById(Long id);

    Optional<Customer> getCustomerByEmail(String email);

    Customer registerCustomer(CustomerRegistrationDto dto);

    Customer updateCustomer(Long id, CustomerUpdateDto updateDto);

    Customer changePassword(Long id, ChangePasswordDto updateDto);

    void deleteCustomer(Long id);
}
