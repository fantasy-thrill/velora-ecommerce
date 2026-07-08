package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final CustomerService customerService;

    @GetMapping
    public String accountHome(Authentication authentication, Model model) {
        String email = authentication.getName();

        model.addAttribute("customer", customerService.getCustomerByEmail(email));

        return "account";
    }

    @GetMapping("/profile")
    public String profile(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        CustomerProfileDto profile = customerService.getCustomerProfile(email);
        CustomerUpdateDto update = customerService.getCustomerForUpdate(email);

        model.addAttribute("profile", profile);
        model.addAttribute("updateProfile", update);
        model.addAttribute("changePassword", new ChangePasswordDto());

        return "account/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            Authentication authentication,
            @Valid
            @ModelAttribute("updateProfile")
            CustomerUpdateDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            // Reload profile card data
            model.addAttribute("profile", customerService.getCustomerProfile(authentication.getName()));
            model.addAttribute("changePassword", new ChangePasswordDto());

            return "account/profile";
        }

        customerService.updateCustomer(authentication.getName(), dto);

        return "redirect:/account/profile";
    }

    @PostMapping("/profile/password")
    public String changePassword(
            Authentication authentication,
            @Valid
            @ModelAttribute("changePassword")
            ChangePasswordDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "profile",
                    customerService.getCustomerProfile(authentication.getName())
            );

            model.addAttribute(
                    "updateProfile",
                    customerService.getCustomerForUpdate(authentication.getName())
            );

            return "account/profile";
        }

        customerService.changePassword(authentication.getName(), dto);

        return "redirect:/account/profile";
    }
}
