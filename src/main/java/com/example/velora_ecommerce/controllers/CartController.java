package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.CartResponseDto;
import com.example.velora_ecommerce.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String viewCart(Authentication authentication, Model model) {
        String email = authentication.getName();
        CartResponseDto cart = cartService.getCart(email);
        model.addAttribute("cart", cart);

        return "cart";
    }
}
