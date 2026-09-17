package com.example.velora_ecommerce.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class OrderPaymentCardDto {
    private String cardInfo;

    private String processorLogo;
}
