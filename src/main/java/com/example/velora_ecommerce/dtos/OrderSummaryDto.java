package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.entities.*;
import com.example.velora_ecommerce.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderSummaryDto {
    @NotNull
    private Long orderId;

    @NotNull
    private List<OrderItem> orderItems;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private LocalDateTime orderDate;

    @NotNull
    private Long paymentMethodId;

    @NotNull
    private OrderStatus status;
}
