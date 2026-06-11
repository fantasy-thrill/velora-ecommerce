package com.example.velora_ecommerce.services.implementations;

import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.services.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Override
    public BigDecimal calculateCheckoutTotal(Cart cart) {
        BigDecimal subtotal = cart.calculateSubtotal();
        BigDecimal discountedTotal = subtotal.subtract(subtotal.multiply(cart.getDiscount()));
        BigDecimal shippingCost = BigDecimal.ZERO;

        Set<Category> categories = new HashSet<>();

        for (CartItem item : cart.getItems()) {
            Category category = item.getProduct().getCategory();
            categories.add(category);
        }

        for (Category category : categories) {
            shippingCost = shippingCost.add(category.getShippingCost());
        }

        return discountedTotal.add(shippingCost);
    }
}
