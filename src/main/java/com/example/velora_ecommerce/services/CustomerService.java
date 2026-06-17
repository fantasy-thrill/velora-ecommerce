package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.ChangePasswordDto;
import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import com.example.velora_ecommerce.dtos.CustomerUpdateDto;
import com.example.velora_ecommerce.entities.Cart;
import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.repositories.CartRepository;
import com.example.velora_ecommerce.repositories.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final CartRepository cartRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            CartRepository cartRepository
    ) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
    }

    
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    
    public Customer registerCustomer(CustomerRegistrationDto dto) {
        Customer customer = new Customer();
        Cart cart = new Cart();

        customer.setEmail(dto.getEmail());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setStreetAddress(dto.getStreetAddress());
        customer.setCity(dto.getCity());
        customer.setState(dto.getState());

        cart.setCustomer(customer);
        cartRepository.save(cart);

        return customerRepository.save(customer);
    }

    
    public Customer updateCustomer(Long id, CustomerUpdateDto updatedDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        customer.setEmail(updatedDto.getEmail());
        customer.setFirstName(updatedDto.getFirstName());
        customer.setLastName(updatedDto.getLastName());
        customer.setStreetAddress(updatedDto.getStreetAddress());
        customer.setCity(updatedDto.getCity());
        customer.setState(updatedDto.getState());

        return customerRepository.save(customer);
    }

    
    public Customer changePassword(Long id, ChangePasswordDto updateDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        String newPassword = updateDto.getPassword();
        customer.setPassword(passwordEncoder.encode(newPassword));

        return customerRepository.save(customer);
    }

    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
