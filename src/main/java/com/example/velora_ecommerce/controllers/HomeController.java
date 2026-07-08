package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.services.CategoryService;
import com.example.velora_ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("featuredProducts", productService.getAllProducts());

        model.addAttribute("categories", categoryService.getAllCategories());

        return "index";
    }
}