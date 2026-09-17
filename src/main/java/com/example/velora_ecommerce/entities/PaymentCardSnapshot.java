package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.enums.CardProcessor;
import com.example.velora_ecommerce.enums.CardType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCardSnapshot {
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private CardProcessor cardProcessor;

    @NotBlank
    @Column(nullable = false)
    private String lastFourDigits;

    public String displayPaymentCardInfo() {
        return cardProcessor.getDisplayString() + " ending in " + lastFourDigits;
    }
}
