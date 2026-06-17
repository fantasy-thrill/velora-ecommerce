package com.example.velora_ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "gift_cards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCard extends PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String code;

    public String displayString() {
        return "$" + this.getBalance() + " Gift Card";
    }
}
