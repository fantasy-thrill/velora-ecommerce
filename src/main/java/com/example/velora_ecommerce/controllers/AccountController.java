package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.enums.State;
import com.example.velora_ecommerce.services.CustomerDetailsService;
import com.example.velora_ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final CustomerService customerService;
    private final CustomerDetailsService customerDetailsService;

    @GetMapping
    public String accountHome(Authentication authentication, Model model) {
        String email = authentication.getName();

        model.addAttribute("customer", customerService.getCustomerByEmail(email));

        return "account/index";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        String email = authentication.getName();

        CustomerProfileDto customer = customerService.getCustomerProfile(email);
        CustomerUpdateDto update = customerService.getCustomerForUpdate(email);

        model.addAttribute("customer", customer);
        model.addAttribute("states", State.values());
        model.addAttribute("updateProfile", update);
        model.addAttribute("changePassword", new ChangePasswordDto());

        return "account/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            Authentication authentication,
            @Valid @ModelAttribute("updateProfile") CustomerUpdateDto dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // Reload profile card data
            model.addAttribute("customer", customerService.getCustomerProfile(authentication.getName()));
            model.addAttribute("changePassword", new ChangePasswordDto());
            System.out.println("Submission has errors");

            return "account/profile";
        }

        String currentEmail = authentication.getName();
        customerService.updateCustomer(currentEmail, dto);

        if (!currentEmail.equalsIgnoreCase(dto.getEmail())) {
            UserDetails userDetails = customerDetailsService.loadUserByUsername(dto.getEmail());

            Authentication newAuthentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            authentication.getCredentials(),
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        }

        redirectAttributes.addFlashAttribute(
                "updateSuccess",
                "Account information successfully updated!"
        );

        return "redirect:/account/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(
            Authentication authentication,
            @Valid @ModelAttribute("changePassword") ChangePasswordDto dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "customer",
                    customerService.getCustomerProfile(authentication.getName())
            );

            model.addAttribute(
                    "updateProfile",
                    customerService.getCustomerForUpdate(authentication.getName())
            );

            System.out.println("Submission has errors.");
            return "account/profile";
        }

        customerService.changePassword(authentication.getName(), dto);
        System.out.println("Password changed.");
        redirectAttributes.addFlashAttribute("updateSuccess", "Password successfully changed!");

        return "redirect:/account/profile";
    }
}
