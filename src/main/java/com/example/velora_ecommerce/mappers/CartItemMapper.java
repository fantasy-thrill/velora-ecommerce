package com.example.velora_ecommerce.mappers;

import com.example.velora_ecommerce.dtos.CartItemDto;
import com.example.velora_ecommerce.entities.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public static CartItemDto toResponseDto(CartItem item) {
        CartItemDto itemDto = new CartItemDto();

        itemDto.setProductId(item.getProduct().getId());
        itemDto.setProductName(item.getProduct().getName());
        itemDto.setImageUrl(item.getProduct().getImageUrl());
        itemDto.setUnitPrice(item.getProduct().getPrice());
        itemDto.setQuantity(item.getQuantity());
        itemDto.setLineTotal(item.getSubtotal());

        return itemDto;
    }

}
