package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.dtos.CartItemDto;
import com.example.velora_ecommerce.dtos.CartResponseDto;
import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.mappers.CartItemMapper;
import com.example.velora_ecommerce.repositories.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {
    private final CustomerService customerService;

    private final CartRepository cartRepository;

    public CartService(CustomerService customerService, CartRepository cartRepository) {
        this.customerService = customerService;
        this.cartRepository = cartRepository;
    }

    public CartResponseDto getCart(String email) {
        Customer customer = getCustomer(email);

        Cart customerCart = customer.getCart();
        CartResponseDto cartDto = new CartResponseDto();
        List<CartItemDto> dtoItems = customerCart.getItems().stream()
                .map(CartItemMapper::toResponseDto)
                .toList();

        cartDto.setItems(dtoItems);
        cartDto.setSubtotal(customerCart.calculateSubtotal());

        return cartDto;
    }

    public int getCartItemCount(String email) {
        Customer customer = getCustomer(email);
        Cart cart = customer.getCart();

        if (cart == null) return 0;

        return cart.getItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    @Transactional
    public void addItemToCart(String email, Product product, int quantity) {
        Customer customer = getCustomer(email);

        Cart customerCart = customer.getCart();
        CartItem item = new CartItem();

        item.setCart(customerCart);
        item.setProduct(product);
        item.setQuantity(quantity);

        customerCart.addItem(item);
        System.out.println("Service: Item added to cart");
    }

    public void removeItemFromCart(String email, Long cartItemId) {
        Customer customer = getCustomer(email);

        Cart customerCart = customer.getCart();
        CartItem itemToRemove = customerCart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        customerCart.removeItem(itemToRemove);
    }

    public void updateCartItemQuantity(String customerEmail, Long cartItemId, int quantity) {
        Cart customerCart = getCustomer(customerEmail).getCart();

        CartItem itemToUpdate = customerCart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        if (quantity <= 0) {
            customerCart.removeItem(itemToUpdate);
            return;
        }

        itemToUpdate.setQuantity(quantity);
        cartRepository.save(customerCart);
    }

    private Customer getCustomer(String email) {
        return customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
