package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import com.example.velora_ecommerce.entities.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getCustomerById(Long id);

    Optional<Customer> getCustomerByEmail(String email);

    Customer registerCustomer(CustomerRegistrationDto dto);

//    void login(LoginDto dto);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
}
