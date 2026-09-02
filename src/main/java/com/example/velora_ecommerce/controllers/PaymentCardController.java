package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.enums.CardProcessor;
import com.example.velora_ecommerce.enums.CardType;
import com.example.velora_ecommerce.services.PaymentCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account/payment-methods")
@RequiredArgsConstructor
public class PaymentCardController {
    private final PaymentCardService paymentCardService;

    @GetMapping
    public String viewPaymentCards(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<PaymentCardResponseDto> paymentCards = paymentCardService.getPaymentCardsByCustomer(email);

        model.addAttribute("paymentCards", paymentCards);
        model.addAttribute("newPaymentCard", new AddPaymentCardDto());
        model.addAttribute("updatePaymentCard", new UpdatePaymentCardDto());
        model.addAttribute("cardTypes", CardType.values());
        model.addAttribute("processors", CardProcessor.values());
        model.addAttribute("openNewPaymentModal", false);
        model.addAttribute("openEditPaymentModal", false);

        return "account/payment-methods";
    }

    @PostMapping("/add-new-card")
    public String addPaymentCard(
            @Valid @ModelAttribute("newPaymentCard") AddPaymentCardDto cardDto,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        List<String> errors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getObjectName() + ": " + error.getDefaultMessage());
                errors.add(error.getDefaultMessage());
            }

            model.addAttribute(
                    "paymentCards",
                    paymentCardService.getPaymentCardsByCustomer(authentication.getName())
            );
            model.addAttribute("updatePaymentCard", new UpdatePaymentCardDto());
            model.addAttribute("cardTypes", CardType.values());
            model.addAttribute("processors", CardProcessor.values());
            model.addAttribute("addErrorMessages", errors);
            model.addAttribute("openNewPaymentModal", true);

            return "account/payment-methods";
        }

        paymentCardService.addPaymentCard(authentication.getName(), cardDto);
        redirectAttributes.addFlashAttribute("successMessage", "Payment method added successfully!");

        return "redirect:/account/payment-methods";
    }

    @PostMapping("/update-card/{id}")
    public String updatePaymentCard(
            @PathVariable Long id,
            @Valid @ModelAttribute("updatePaymentCard") UpdatePaymentCardDto updateDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        List<String> errors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getObjectName() + ": " + error.getDefaultMessage());
                errors.add(error.getDefaultMessage());
            }

            model.addAttribute(
                    "paymentCards",
                    paymentCardService.getPaymentCardsByCustomer(authentication.getName())
            );
            model.addAttribute("newPaymentCard", new AddPaymentCardDto());
            model.addAttribute("cardTypes", CardType.values());
            model.addAttribute("processors", CardProcessor.values());
            model.addAttribute("editErrorMessages", errors);
            model.addAttribute("openEditPaymentModal", true);

            return "account/payment-methods";
        }

        paymentCardService.updatePaymentCard(authentication.getName(), id, updateDto);
        redirectAttributes.addFlashAttribute("successMessage", "Payment method updated successfully!");

        return "redirect:/account/payment-methods";
    }

    @PostMapping("/delete-card/{id}")
    public String deletePaymentCard(
            @PathVariable Long id,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        paymentCardService.deletePaymentCard(authentication.getName(), id);
        redirectAttributes.addFlashAttribute("successMessage", "Payment method removed successfully.");

        return "redirect:/account/payment-methods";
    }
}
