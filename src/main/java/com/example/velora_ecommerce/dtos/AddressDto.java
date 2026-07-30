package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.State;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String street;

    private String city;

    private State state;

    private String zipCode;
}
