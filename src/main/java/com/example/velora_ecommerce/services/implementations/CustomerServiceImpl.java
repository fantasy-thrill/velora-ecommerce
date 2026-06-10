package com.example.velora_ecommerce.services.implementations;

import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.repositories.CustomerRepository;
import com.example.velora_ecommerce.services.CustomerService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer registerCustomer(CustomerRegistrationDto dto) {
        Customer customer = new Customer();

        customer.setEmail(dto.getEmail());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setStreetAddress(dto.getStreetAddress());
        customer.setCity(dto.getCity());
        customer.setState(dto.getState());

        return customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles("CUSTOMER")
                .build();
    }

    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        customer.setEmail(updatedCustomer.getEmail());
        customer.setPassword(passwordEncoder.encode(updatedCustomer.getPassword()));
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setStreetAddress(updatedCustomer.getStreetAddress());
        customer.setCity(updatedCustomer.getCity());
        customer.setState(updatedCustomer.getState());

        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
