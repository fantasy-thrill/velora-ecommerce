package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.entities.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    Cart createCart(Cart cart);

    void deleteCart(Cart cart);

    BigDecimal calculateCheckoutTotal(Cart cart);
}
