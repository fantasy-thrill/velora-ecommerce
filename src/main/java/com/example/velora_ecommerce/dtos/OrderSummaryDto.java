package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderSummaryDto {
    private Long orderId;

    private List<OrderItem> orderItems; // To be revised

    private BigDecimal totalPrice;

    private LocalDateTime orderDate;

    private String paymentMethodDisplay;

    private OrderStatus status;
}
