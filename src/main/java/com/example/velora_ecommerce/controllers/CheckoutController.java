package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.entities.Order;
import com.example.velora_ecommerce.entities.PaymentCard;
import com.example.velora_ecommerce.enums.CardProcessor;
import com.example.velora_ecommerce.enums.CardType;
import com.example.velora_ecommerce.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
@SessionAttributes("checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    private final PaymentCardService paymentCardService;

    private final OrderService orderService;

    @GetMapping
    public String displayCheckoutPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        CheckoutPageDto checkoutPage = checkoutService.getCheckoutPage(email);

        if (!model.containsAttribute("checkout")) {
            CheckoutDto checkout = new CheckoutDto();

            checkout.setAddress(checkoutPage.getAddress());
            checkout.setPaymentMethodId(checkoutPage.getSelectedPaymentMethod().getPaymentCardId());
            checkout.setShippingSpeed(checkoutPage.getShippingSpeed());

            model.addAttribute("checkout", checkout);
        }

        model.addAttribute("checkoutPage", checkoutPage);
        model.addAttribute("newPaymentMethod", new PaymentCardDto());
        model.addAttribute("cardTypes", CardType.values());
        model.addAttribute("cardProcessors", CardProcessor.values());
        model.addAttribute("newAddress", new AddressDto());
        model.addAttribute("giftCard", new GiftCardDto());

        CheckoutSummaryDto summary = checkoutService.buildCheckoutSummary(
                email,
                (CheckoutDto) model.getAttribute("checkout")
        );
        model.addAttribute("summary", summary);

        return "checkout";
    }

    @PostMapping("/address/new")
    public String addAddress(
            @Valid @ModelAttribute("newAddress") AddressDto addressDto,
            BindingResult bindingResult,
            @ModelAttribute("checkout") CheckoutDto checkoutDto
    ) {
        if (bindingResult.hasErrors()) {
            return "checkout";
        }

        checkoutDto.setAddress(addressDto);
        return "redirect:/checkout";
    }

    @PostMapping("/payment/new")
    public String addPaymentMethod(
            Authentication authentication,
            @Valid @ModelAttribute("newPaymentMethod") PaymentCardDto paymentDto,
            BindingResult bindingResult,
            @ModelAttribute("checkout") CheckoutDto checkoutDto
    ) {
        if (bindingResult.hasErrors()) {
            return "checkout";
        }

        PaymentCard card = paymentCardService.addPaymentCard(authentication.getName(), paymentDto);
        checkoutDto.setPaymentMethodId(card.getId());

        return "redirect:/checkout";
    }

    @PostMapping("/shipping")
    public String updateShipping(@ModelAttribute("checkout") CheckoutDto checkoutDto) {
        return "redirect:/checkout";
    }

    @PostMapping("/gift-card")
    public String applyGiftCard(@ModelAttribute("checkout") CheckoutDto checkoutDto) {
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(
            Authentication authentication,
            @ModelAttribute("checkout") CheckoutDto checkoutDto,
            SessionStatus sessionStatus,
            @ModelAttribute("summary") CheckoutSummaryDto summaryDto
    ) {
        Order order = orderService.placeOrder(authentication.getName(), checkoutDto, summaryDto);
        sessionStatus.setComplete();

        return "redirect:/checkout/confirmation" + order.getId();
    }

    @GetMapping("/confirmation/{orderId}")
    public String orderConfirmation(@PathVariable Long orderId, Authentication authentication, Model model) {
        String confirmation = checkoutService.displayConfirmation(authentication.getName(), orderId);
        model.addAttribute("confirmation", confirmation);

        return "checkout/confirmation";
    }
}
