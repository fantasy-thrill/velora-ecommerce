package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.enums.CardType;
import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class PaymentMethod {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public String displayString() {
        return "";
    }
}
