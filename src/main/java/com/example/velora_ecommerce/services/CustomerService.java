package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.entities.Address;
import com.example.velora_ecommerce.entities.Cart;
import com.example.velora_ecommerce.entities.Customer;
import com.example.velora_ecommerce.mappers.CustomerMapper;
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

    public CustomerProfileDto getCustomerProfile(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        return CustomerMapper.toProfileDto(customer);
    }

    public CustomerUpdateDto getCustomerForUpdate(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        return CustomerMapper.toUpdateDto(customer);
    }

    public void registerCustomer(CustomerRegistrationDto dto) {
        Customer customer = new Customer();
        Cart cart = new Cart();

        customer.setEmail(dto.getEmail());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhoneNumber(dto.getPhoneNumber());

        Address address = new Address();
        Address dtoAddress = dto.getAddress();
        
        address.setCustomerFirstName(dto.getFirstName());
        address.setCustomerLastName(dto.getLastName());
        address.setStreet(dtoAddress.getStreet());
        address.setCity(dtoAddress.getCity());
        address.setState(dtoAddress.getState());
        address.setZipCode(dtoAddress.getZipCode());

        customer.setAddress(address);

        cart.setCustomer(customer);
        cartRepository.save(cart);

        customerRepository.save(customer);
    }

    
    public void updateCustomer(String email, CustomerUpdateDto updatedDto) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        customer.setEmail(updatedDto.getEmail());
        customer.setFirstName(updatedDto.getFirstName());
        customer.setLastName(updatedDto.getLastName());

        Address address = customer.getAddress();
        AddressDto addressDto = updatedDto.getAddress();
        
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setZipCode(addressDto.getZipCode());

        customerRepository.save(customer);
    }

    
    public void changePassword(String email, ChangePasswordDto updateDto) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        String newPassword = updateDto.getPassword();
        customer.setPassword(passwordEncoder.encode(newPassword));

        customerRepository.save(customer);
    }

    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
