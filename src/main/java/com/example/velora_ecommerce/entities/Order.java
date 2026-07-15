package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.dtos.CheckoutSummaryDto;
import com.example.velora_ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Embedded
    @NotNull
    private Address shippingAddress;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull
    private CheckoutSummaryDto summary;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_card_id", nullable = false)
    private PaymentCard paymentCard;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gift_card_id", nullable = true)
    private GiftCard giftCard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
}
