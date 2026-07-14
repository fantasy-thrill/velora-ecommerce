package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.entities.PaymentCard;
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
//    private final CustomerService customerService;

    private final CheckoutService checkoutService;

    private final PaymentCardService paymentCardService;

    private final OrderService orderService;

    @GetMapping
    public String displayCheckoutPage(Authentication authentication, Model model) {
        String email = authentication.getName();

        if (!model.containsAttribute("checkout")) {
            CheckoutDto checkout = new CheckoutDto();
//            Customer customer = customerService.getCustomerByEmail(email)
//                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
//
//            CustomerProfileDto customerDto = CustomerMapper.toProfileDto(customer);
//            checkout.setAddress(customerDto.getAddress());
//            checkout.setPaymentMethodId(customer.getPaymentCards().getFirst().getId());

            checkout.setPaymentMethodId(checkoutService.getCheckoutPage(email)
                    .getSelectedPaymentMethod()
                    .getPaymentCardId()
            );

            model.addAttribute("checkout", checkout);
        }

        model.addAttribute("checkoutPage", checkoutService.getCheckoutPage(email));
        model.addAttribute("newPaymentMethod", new PaymentCardDto());
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
            Authentication authentication,
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
        orderService.placeOrder(authentication.getName(), checkoutDto, summaryDto);
        sessionStatus.setComplete();

        return "redirect:/orders";
    }
}
