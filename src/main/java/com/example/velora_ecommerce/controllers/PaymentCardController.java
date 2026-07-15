package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.PaymentCardDto;
import com.example.velora_ecommerce.dtos.PaymentCardResponseDto;
import com.example.velora_ecommerce.mappers.PaymentCardMapper;
import com.example.velora_ecommerce.services.PaymentCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/payment-methods")
@RequiredArgsConstructor
public class PaymentCardController {
    private final PaymentCardService paymentCardService;

    @GetMapping
    public String viewPaymentCards(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<PaymentCardResponseDto> paymentCards = paymentCardService.getPaymentCardsByCustomer(email).stream()
                .map(PaymentCardMapper::toResponseDto)
                .toList();

        model.addAttribute("paymentCards", paymentCards);

        return "payment_methods";
    }

    @PostMapping("/new")
    public String addPaymentCard(
            @Valid @ModelAttribute("newPaymentMethod") PaymentCardDto paymentCardDto,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "paymentCards",
                    paymentCardService.getPaymentCardsByCustomer(authentication.getName())
            );

            return "payment_methods";
        }

        paymentCardService.addPaymentCard(authentication.getName(), paymentCardDto);
        redirectAttributes.addFlashAttribute("successMessage", "Payment method added successfully!");

        return "redirect:/payment-methods";
    }

    @PostMapping("/{id}/update")
    public String updatePaymentCard(
            @PathVariable Long id,
            @Valid @ModelAttribute("paymentCard") PaymentCardDto paymentCardDto,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "payment_methods";
        }

        paymentCardService.updatePaymentCard(authentication.getName(), id, paymentCardDto);
        redirectAttributes.addFlashAttribute("successMessage", "Payment method updated successfully!");

        return "redirect:/payment-methods";
    }

    @PostMapping("/{id}/delete")
    public String deletePaymentCard(
            @PathVariable Long id,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        paymentCardService.deletePaymentCard(authentication.getName(), id);
        redirectAttributes.addFlashAttribute("successMessage", "Payment method removed successfully.");

        return "redirect:/payment-methods";
    }
}
