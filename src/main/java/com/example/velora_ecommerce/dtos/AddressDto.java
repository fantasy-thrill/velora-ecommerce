package com.example.velora_ecommerce.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String street;

    private String city;

    private String state;

    private String zipCode;
}
