package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDto {
    private Long orderId;

    private List<OrderItemDto> orderItems;

    private AddressDto shippingAddress;

    private BigDecimal totalPrice;

    private Map<String, BigDecimal> summary;

    private LocalDateTime orderDate;

    private String paymentMethod;

    private OrderStatus status;
}
