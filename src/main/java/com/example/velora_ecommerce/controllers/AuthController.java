package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.enums.State;
import com.example.velora_ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CustomerService customerService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerCustomer", new CustomerRegistrationDto());
        model.addAttribute("states", State.values());

        return "auth/register";
    }

//    @GetMapping("/registration-successful")
//    public String registrationConfirmation() {
//        return ;
//    }

    @PostMapping("/register")
    public String registerCustomer(
            Model model,
            @Valid @ModelAttribute("registerCustomer") CustomerRegistrationDto dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("states", State.values());
            return "auth/register";
        }

        customerService.registerCustomer(dto);
        return "auth/registration-successful";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("forgotPasswordEmail", new ForgotPasswordEmailDto());

        return "auth/forgot-password";
    }

    @PostMapping("/verify-email")
    @ResponseBody
    public VerifyEmailResponse verifyEmail(
            @Valid @RequestBody ForgotPasswordEmailDto emailDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new VerifyEmailResponse(false, "Please enter a valid email address.");
        }

        boolean accountExists = customerService.accountWithEmailExists(emailDto.getEmail());

        if (!accountExists) {
            return new VerifyEmailResponse(accountExists, "Invalid e-mail address");
        }

        return new VerifyEmailResponse(accountExists, null);
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public PasswordResetResponse resetPassword(
            @RequestParam String email,
            @Valid @RequestBody ChangePasswordDto dto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return new PasswordResetResponse(false, "Please correct the highlighted fields.");
        }

        customerService.changePassword(email, dto);

        return new PasswordResetResponse(true, "Password updated successfully.");
    }

    public record VerifyEmailResponse(
            boolean exists,
            String errorMessage
    ) {}

    public record PasswordResetResponse(
            boolean success,
            String message
    ) {}
}
