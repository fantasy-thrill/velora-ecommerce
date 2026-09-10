package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.config.SecurityConfig;
import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import com.example.velora_ecommerce.services.CartService;
import com.example.velora_ecommerce.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    private CartService cartService;

    @Test
    void shouldRegisterCustomerSuccessfully() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "johndoe@gmail.com")
                        .param("phoneNumber", "5551234567")
                        .param("password", "Password123!")
                        .param("confirmPassword", "Password123!")
                        .param("address.street", "123 Main St")
                        .param("address.city", "Dallas")
                        .param("address.state", "TX")
                        .param("address.zipCode", "75001"))

                .andExpect(status().isOk())
                .andExpect(view().name("auth/registration-successful"));

        verify(customerService).registerCustomer(any(CustomerRegistrationDto.class));
    }

    @Test
    void shouldRejectRegistrationWithInvalidInput() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "johndoe@gmail.com")
                        .param("phoneNumber", "5551234567")
                        .param("password", "Password123!")
                        .param("confirmPassword", "Password12")
                        .param("address.street", "123 Main St")
                        .param("address.city", "Dallas")
                        .param("address.state", "TX")
                        .param("address.zipCode", "75001"))

                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"));
//                .andExpect(model().attributeHasFieldErrors("registerCustomer", "confirmPassword"));

        verify(customerService, never()).registerCustomer(any());
    }
}
