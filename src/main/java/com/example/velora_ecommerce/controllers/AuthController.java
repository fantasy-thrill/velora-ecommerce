package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import com.example.velora_ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private CustomerService customerService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute(
                "registerCustomer",
                new CustomerRegistrationDto());

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerCustomer(
            @Valid @ModelAttribute("registerUser")
            CustomerRegistrationDto dto) {

        customerService.registerCustomer(dto);

        return "redirect:/auth/login";
    }
}
