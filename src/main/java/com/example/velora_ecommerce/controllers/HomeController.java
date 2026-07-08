package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.enums.Category;
import com.example.velora_ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("featuredProducts", productService.getFeaturedProducts());
        model.addAttribute("categories", Category.values());

        return "index";
    }
}