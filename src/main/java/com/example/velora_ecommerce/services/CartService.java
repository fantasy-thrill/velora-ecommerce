package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.CartItemDto;
import com.example.velora_ecommerce.dtos.CartResponseDto;
import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.mappers.CartItemMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CustomerService customerService;

    public CartService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CartResponseDto getCart(String email) {
        Customer customer = customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Cart customerCart = customer.getCart();
        CartResponseDto cartDto = new CartResponseDto();
        List<CartItemDto> dtoItems = customerCart.getItems().stream()
                .map(CartItemMapper::toResponseDto)
                .toList();

        cartDto.setItems(dtoItems);
        cartDto.setSubtotal(customerCart.calculateSubtotal());

        return cartDto;
    }
}
