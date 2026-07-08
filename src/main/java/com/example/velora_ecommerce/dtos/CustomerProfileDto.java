package com.example.velora_ecommerce.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfileDto {
    private String firstName;

    private String lastName;

    private String email;

    private AddressDto address;

    private String phoneNumber;
}
