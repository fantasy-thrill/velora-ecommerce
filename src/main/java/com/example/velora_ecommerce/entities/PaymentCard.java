package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.enums.CardProcessor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_cards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCard extends PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardProcessor cardProcessor;

    @Column(nullable = false)
    private String lastFourDigits;

    public String displayString() {
        return this.getCardProcessor() + " ending in " + this.getLastFourDigits();
    }
}
