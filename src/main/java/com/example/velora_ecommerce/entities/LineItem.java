package com.example.velora_ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class LineItem {
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public BigDecimal getSubtotal() {
        return product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));
    }
}
