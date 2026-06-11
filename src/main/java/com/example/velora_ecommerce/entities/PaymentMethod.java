package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.enums.CardType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_method")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    @Column(nullable = false)
    private String lastFourDigits;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
