package com.example.velora_ecommerce.services.implementations;

import com.example.velora_ecommerce.entities.Cart;
import com.example.velora_ecommerce.entities.CartItem;
import com.example.velora_ecommerce.entities.Category;
import com.example.velora_ecommerce.repositories.CartRepository;
import com.example.velora_ecommerce.services.CartService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }

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
