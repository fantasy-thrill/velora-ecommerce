package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.enums.CardProcessor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private String cardholderName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardProcessor cardProcessor;

    @Column(nullable = false)
    private String lastFourDigits;

    @Column(nullable = false)
    private LocalDate expirationDate;

    public String displayString() {
        return this.getCardProcessor().getDisplayString() + " ending in " + this.getLastFourDigits();
    }
}
