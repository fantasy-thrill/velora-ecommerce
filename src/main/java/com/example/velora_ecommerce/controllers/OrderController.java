package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.OrderSummaryDto;
import com.example.velora_ecommerce.mappers.OrderMapper;
import com.example.velora_ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public String orderHistory(Authentication authentication, Model model) {
        String email = authentication.getName();

        List<OrderSummaryDto> orders = orderService.getOrdersForCustomer(email);
        model.addAttribute("orders", orders);

        return "account/orders";
    }

    @GetMapping("/{id}")
    public String orderDetails(@PathVariable Long id, Authentication authentication, Model model) {
        String email = authentication.getName();

        OrderSummaryDto order = orderService.getOrderDetails(email, id);
        model.addAttribute("order", order);

        return "account/order-details";
    }

    @PostMapping("/cancel-order/{id}")
    public String cancelOrder(
            @PathVariable Long id,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        String email = authentication.getName();

        orderService.cancelOrder(email, id);
        redirectAttributes.addFlashAttribute("successMessage","Your order has been canceled.");

        return "redirect:/orders/" + id;
    }
}
