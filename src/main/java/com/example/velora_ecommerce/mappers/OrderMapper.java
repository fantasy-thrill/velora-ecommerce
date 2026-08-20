package com.example.velora_ecommerce.mappers;

import com.example.velora_ecommerce.dtos.*;
import com.example.velora_ecommerce.entities.Order;
import com.example.velora_ecommerce.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    public static OrderSummaryDto toResponseDto(Order order) {
        OrderSummaryDto orderDto = new OrderSummaryDto();
        AddressDto addressDto = new AddressDto();
        List<OrderItemDto> orderItems = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            OrderItemDto itemDto = new OrderItemDto();

            itemDto.setId(item.getId());
            itemDto.setProduct(ProductMapper.toResponseDto(item.getProduct()));
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPurchasePrice(item.getPurchasePrice());

            orderItems.add(itemDto);
        }

        addressDto.setCustomerFullName(
                order.getShippingAddress().getCustomerFirstName() + " " +
                order.getShippingAddress().getCustomerLastName()
        );
        addressDto.setStreet(order.getShippingAddress().getStreet());
        addressDto.setCity(order.getShippingAddress().getCity());
        addressDto.setState(order.getShippingAddress().getState());
        addressDto.setZipCode(order.getShippingAddress().getZipCode());

        orderDto.setOrderId(order.getId());
        orderDto.setOrderItems(orderItems);
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderDate(order.getDate());
        orderDto.setShippingAddress(addressDto);
        orderDto.setSummary(order.getSummary());
        orderDto.setPaymentMethod(order.getPaymentCard().displayString());
        orderDto.setStatus(order.getStatus());

        return orderDto;
    }
}
