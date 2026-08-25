package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(name = "order_summary", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "charge_name")
    @Column(name = "charge_value")
    private Map<String, BigDecimal> summary = new HashMap<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_card_id", nullable = false)
    private PaymentCard paymentCard;

    @ManyToOne
    @JoinColumn(name = "gift_card_id")
    private GiftCard giftCard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
}