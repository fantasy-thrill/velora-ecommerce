package com.example.velora_ecommerce.services;

import com.example.velora_ecommerce.entities.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    BigDecimal calculateCheckoutTotal(Cart cart);
}
