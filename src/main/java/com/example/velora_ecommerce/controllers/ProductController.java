package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.ProductResponseDto;
import com.example.velora_ecommerce.dtos.ProductFilterDto;
import com.example.velora_ecommerce.entities.Product;
import com.example.velora_ecommerce.enums.*;
import com.example.velora_ecommerce.mappers.ProductMapper;
import com.example.velora_ecommerce.services.CartService;
import com.example.velora_ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final CartService cartService;

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam String query,
            @RequestParam(required = false) List<Brand> brands,
            @RequestParam(required = false) SortOption sort,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        ProductFilterDto searchDto = new ProductFilterDto(query, brands, sort, page);
        Page<ProductResponseDto> results = productService.searchProducts(searchDto);
        model.addAttribute("searchQuery", query);
        model.addAttribute("results", results);

        return "products/search";
    }

    @GetMapping("/category/{category}")
    public String productsByCategory(@PathVariable Category category, Model model) {
        Page<ProductResponseDto> results = productService.getProductsByCategory(category);

        model.addAttribute("results", results);
        model.addAttribute("category", category);

        return "products/category";
    }

    @GetMapping
    public String allProducts(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<ProductResponseDto> results = productService.getAllProducts(page, 12);
        model.addAttribute("results", results);

        return "products/index";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        ProductResponseDto product = ProductMapper.toResponseDto(productService.getProductById(id));
        model.addAttribute("product", product);

        return "products/details";
    }

    @PostMapping("/{id}/add-to-cart")
    public String addToCart(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        String email = authentication.getName();
        Product product = productService.getProductById(id);
        cartService.addItemToCart(email, product, quantity);

        redirectAttributes.addFlashAttribute("cartSuccess", "Item added to your cart");

        return "redirect:/products/" + id;
    }
}
