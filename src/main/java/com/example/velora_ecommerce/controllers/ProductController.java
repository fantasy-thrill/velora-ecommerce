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
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) List<Brand> brands,
            @RequestParam(required = false) SortOption sortOption,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        ProductFilterDto searchDto = new ProductFilterDto(query, category, brands, sortOption, page);
        Page<ProductResponseDto> results = productService.searchProducts(searchDto);

        addListingAttributes(model, searchDto, results);
        model.addAttribute("formAction", "/products/search");

        return "products/search";
    }

    @GetMapping("/all")
    public String allProducts(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) List<Brand> brands,
            @RequestParam(required = false) SortOption sortOption,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        ProductFilterDto filterDto = new ProductFilterDto(null, category, brands, sortOption, page);
        Page<ProductResponseDto> results = productService.getAllProducts(filterDto);

        addListingAttributes(model, filterDto, results);
        model.addAttribute("formAction", "/products/all");

        return "products/search";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Product productEntity = productService.getProductById(id);
        ProductResponseDto product = ProductMapper.toResponseDto(productEntity);
        List<ProductResponseDto> similarProducts = productService.getSimilarProducts(productEntity);

        model.addAttribute("product", product);
        model.addAttribute("similarProducts", similarProducts);
        model.addAttribute("categories", Category.values());

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

        System.out.println("Controller: Item added to cart");
        return "redirect:/products/" + id;
    }

    private void addListingAttributes(Model model, ProductFilterDto filterDto, Page<ProductResponseDto> results) {
        model.addAttribute("filterDto", filterDto);
        model.addAttribute("results", results);
        model.addAttribute("categories", Category.values());
        model.addAttribute("brands", Brand.values());
    }
}
