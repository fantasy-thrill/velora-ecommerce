package com.example.velora_ecommerce.controllers;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.entities.GiftCard;
import com.example.velora_ecommerce.entities.Order;
import com.example.velora_ecommerce.entities.PaymentCard;
import com.example.velora_ecommerce.enums.*;
import com.example.velora_ecommerce.mappers.PaymentCardMapper;
import com.example.velora_ecommerce.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
@SessionAttributes({"checkout", "summary"})
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final PaymentCardService paymentCardService;
    private final GiftCardService giftCardService;
    private final OrderService orderService;

    @GetMapping
    public String displayCheckoutPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        CheckoutPageDto checkoutPage = checkoutService.getCheckoutPage(email);

        if (!model.containsAttribute("checkout")) {
            CheckoutDto checkout = new CheckoutDto();
            model.addAttribute("checkout", checkout);
//            System.out.println("Current value of shipping speed: " + checkout.getShippingSpeed());
        }

        model.addAttribute("address", checkoutPage.getAddress());
        model.addAttribute("states", State.values());
        model.addAttribute("paymentCards", checkoutPage.getPaymentCards());
        model.addAttribute("newPaymentMethod", new AddPaymentCardDto());
        model.addAttribute("cardTypes", CardType.values());
        model.addAttribute("cardProcessors", CardProcessor.values());
        model.addAttribute("newAddress", new AddressDto());
        model.addAttribute("shippingSpeed", ShippingSpeed.values());
        model.addAttribute("giftCard", new GiftCardDto());
        model.addAttribute("items", checkoutPage.getItems());

        CheckoutSummaryDto summary = checkoutService.buildCheckoutSummary(
                email,
                (CheckoutDto) model.getAttribute("checkout")
        );
        model.addAttribute("summary", summary);

        return "checkout";
    }

    @GetMapping("/start")
    public String startCheckout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();

        return "redirect:/checkout";
    }

    @PostMapping("/select-address")
    @ResponseBody
    public ResponseEntity<Void> selectAddress(
            @Valid @RequestBody AddressDto addressDto,
            BindingResult bindingResult,
            @ModelAttribute("checkout") CheckoutDto checkoutDto
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        checkoutDto.setAddress(addressDto);
        System.out.println("Address saved to checkout session.");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/address/new")
    @ResponseBody
    public ResponseEntity<AddressDto> addAddress(
            @Valid @ModelAttribute("newAddress") AddressDto addressDto,
            BindingResult bindingResult,
            @ModelAttribute("checkout") CheckoutDto checkoutDto
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        checkoutDto.setAddress(addressDto);
        System.out.print(
                "New address added:\n" +
                checkoutDto.getAddress().getCustomerFullName() + "\n" +
                checkoutDto.getAddress().getStreet() + "\n" +
                checkoutDto.getAddress().getCity() + ", " +
                checkoutDto.getAddress().getState() + " " +
                checkoutDto.getAddress().getZipCode()
        );

        return ResponseEntity.ok(addressDto);
    }

    @PostMapping("/select-payment")
    @ResponseBody
    public ResponseEntity<Void> selectPaymentMethod(
            @RequestParam Long paymentMethodId,
            @ModelAttribute("checkout") CheckoutDto checkoutDto
    ) {
        checkoutDto.setPaymentMethodId(paymentMethodId);

        System.out.println("Payment method added to checkout session.");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment/new")
    @ResponseBody
    public ResponseEntity<PaymentCardResponseDto> addPaymentMethod(
            Authentication authentication,
            @Valid @ModelAttribute("newPaymentMethod") AddPaymentCardDto paymentDto,
            BindingResult bindingResult,
            @ModelAttribute("checkout") CheckoutDto checkoutDto
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        PaymentCard card = paymentCardService.addPaymentCard(authentication.getName(), paymentDto);
        PaymentCardResponseDto newCardDto = PaymentCardMapper.toResponseDto(card);

        checkoutDto.setPaymentMethodId(card.getId());
        System.out.print("New payment card added: " + card.getCardProcessor() + " ending in " + card.getLastFourDigits());

        return ResponseEntity.ok(newCardDto);
    }

    @PostMapping("/shipping")
    @ResponseBody
    public CheckoutSummaryDto selectShippingSpeed(
            @RequestParam ShippingSpeed shippingSpeed,
            @ModelAttribute("checkout") CheckoutDto checkoutDto,
            @ModelAttribute("summary") CheckoutSummaryDto summaryDto,
            Authentication authentication
    ) {
        checkoutDto.setShippingSpeed(shippingSpeed);

        CheckoutSummaryDto updatedSummary = checkoutService.buildCheckoutSummary(authentication.getName(), checkoutDto);
        summaryDto = updatedSummary;

        System.out.println("Shipping speed updated: " + checkoutDto.getShippingSpeed() + " $" + summaryDto.getShipping());
        System.out.println("New total: $" + summaryDto.getTotal());
        return summaryDto;
    }

    @PostMapping("/gift-card")
    @ResponseBody
    public ResponseEntity<Object> applyGiftCard(
            @ModelAttribute("checkout") CheckoutDto checkoutDto,
            @ModelAttribute("giftCard") GiftCardDto giftCardDto,
            BindingResult bindingResult,
            @ModelAttribute("summary") CheckoutSummaryDto summaryDto,
            Authentication authentication
    ) {
        if (!giftCardService.giftCardExists(giftCardDto.getCode())) {
            String errorMessage = "Gift card does not exist";
            bindingResult.rejectValue("code", "code.invalid", errorMessage);

            return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
        }

        checkoutDto.setGiftCardCode(giftCardDto.getCode());
        CheckoutSummaryDto updatedSummary = checkoutService.buildCheckoutSummary(authentication.getName(), checkoutDto);

        summaryDto = updatedSummary;
        System.out.println("Gift card added. Checkout summary updated.");
        System.out.println("$" + summaryDto.getGiftCardAmount() + " subtracted from total.");

        return ResponseEntity.ok(summaryDto);
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

        return "redirect:/checkout/confirmation/" + order.getId();
    }

    @GetMapping("/confirmation/{orderId}")
    public String orderConfirmation(@PathVariable Long orderId, Authentication authentication, Model model) {
        String confirmation = checkoutService.displayConfirmation(authentication.getName(), orderId);

        model.addAttribute("confirmation", confirmation);
        model.addAttribute("orderId", orderId);

        return "order-confirmation";
    }
}
