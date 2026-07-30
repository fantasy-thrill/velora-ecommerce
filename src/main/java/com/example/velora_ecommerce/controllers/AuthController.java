package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import com.example.velora_ecommerce.enums.State;
import com.example.velora_ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/register")
    public String registerCustomer(
            @Valid @ModelAttribute("registerCustomer")
            CustomerRegistrationDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "auth/register";

        customerService.registerCustomer(dto);

        return "redirect:/auth/login";
    }
}
