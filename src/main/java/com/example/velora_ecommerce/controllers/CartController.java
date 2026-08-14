package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.CartResponseDto;
import com.example.velora_ecommerce.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/remove-item/{cartItemId}")
    public String removeItem(
            @PathVariable Long cartItemId,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        cartService.removeItemFromCart(authentication.getName(), cartItemId);
        redirectAttributes.addFlashAttribute("cartMessage", "Item removed from your cart");

        return "redirect:/cart";
    }

    @PostMapping("/update-quantity/{cartItemId}")
    public String updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        cartService.updateCartItemQuantity(authentication.getName(), cartItemId, quantity);
        redirectAttributes.addFlashAttribute("cartMessage", "Cart updated successfully");

        return "redirect:/cart";
    }

//    @GetMapping("/checkout")
//    public String proceedToCheckout() {
//        return "redirect:/checkout";
//    }
}
