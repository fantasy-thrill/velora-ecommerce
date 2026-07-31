package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.ChangePasswordDto;
import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.enums.State;
import com.example.velora_ecommerce.repositories.CartRepository;
import com.example.velora_ecommerce.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldRegisterCustomerSuccessfully() {
        // Arrange
        CustomerRegistrationDto dto = new CustomerRegistrationDto();

        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john@example.com");
        dto.setPhoneNumber("555-123-4567");
        dto.setPassword("Password123!");
        dto.setConfirmPassword("Password123!");

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Dallas");
        address.setState(State.TX);
        address.setZipCode("75001");

        dto.setAddress(address);

        when(passwordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPassword");

        // Act
        customerService.registerCustomer(dto);

        // Assert

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());
        Customer savedCustomer = customerCaptor.getValue();

        assertEquals("John", savedCustomer.getFirstName());
        assertEquals("Doe", savedCustomer.getLastName());
        assertEquals("john@example.com", savedCustomer.getEmail());
        assertEquals("encodedPassword", savedCustomer.getPassword());
    }

    @Test
    void shouldEncodePasswordBeforeSavingCustomer() {
        CustomerRegistrationDto dto = new CustomerRegistrationDto();

        dto.setPassword("Password123!");
        dto.setConfirmPassword("Password123!");
        dto.setAddress(new Address());

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        customerService.registerCustomer(dto);
        verify(passwordEncoder).encode("Password123!");
    }

    @Test
    void shouldSaveCustomerToRepository() {
        CustomerRegistrationDto dto = new CustomerRegistrationDto();
        dto.setAddress(new Address());

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        customerService.registerCustomer(dto);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void shouldCreateCartWhenRegisteringCustomer() {
        CustomerRegistrationDto dto = new CustomerRegistrationDto();
        dto.setAddress(new Address());

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        customerService.registerCustomer(dto);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void shouldAssociateCartWithCustomer() {
        CustomerRegistrationDto dto = new CustomerRegistrationDto();
        dto.setAddress(new Address());

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        customerService.registerCustomer(dto);
        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartCaptor.capture());
        Cart cart = cartCaptor.getValue();

        assertEquals("encodedPassword", cart.getCustomer().getPassword());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        CustomerRegistrationDto dto = new CustomerRegistrationDto();
        dto.setEmail("john@example.com");

        when(customerRepository.existsByEmail("john@example.com"))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.registerCustomer(dto)
        );

        verify(customerRepository, never()).save(any());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void shouldChangePasswordSuccessfully() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("john@example.com");
        customer.setPassword("oldPassword");

        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setPassword("NewPassword123!");
        dto.setConfirmPassword("NewPassword123!");

        when(customerRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        when(passwordEncoder.encode("NewPassword123!"))
                .thenReturn("encodedPassword");

        // Act
        customerService.changePassword("john@example.com", dto);

        // Assert
        assertEquals("encodedPassword", customer.getPassword());

        verify(passwordEncoder).encode("NewPassword123!");
        verify(customerRepository).save(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerDoesNotExist() {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setPassword("Password123!");
        dto.setConfirmPassword("Password123!");

        when(customerRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> customerService.changePassword("john@example.com", dto)
        );

        verify(customerRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPasswordsDoNotMatch() {
        Customer customer = new Customer();
        customer.setEmail("john@example.com");

        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setPassword("Password123!");
        dto.setConfirmPassword("DifferentPassword");

        when(customerRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.changePassword("john@example.com", dto)
        );

        verify(passwordEncoder, never()).encode(any());
        verify(customerRepository, never()).save(any());
    }
}
