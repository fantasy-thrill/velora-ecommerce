package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.enums.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String customerFullName;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotNull
    private State state;

    @NotBlank
    @Pattern(
            regexp = "^\\d{5}$",
            message = "ZIP code must be exactly 5 digits long."
    )
    private String zipCode;
}
