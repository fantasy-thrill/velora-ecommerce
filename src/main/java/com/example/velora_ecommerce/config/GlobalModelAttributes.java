package com.example.velora_ecommerce.config;

import com.example.velora_ecommerce.services.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {
    private final CartService cartService;

    public GlobalModelAttributes(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute
    public void addCartItemCount(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String customerEmail = authentication.getName();
            int cartItemCount = cartService.getCartItemCount(customerEmail);

            model.addAttribute("cartItemCount", cartItemCount);
        }
    }
}
