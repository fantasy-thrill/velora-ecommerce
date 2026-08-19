package com.example.velora_ecommerce.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CustomerUpdateDto {
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "E-mail is required.")
    @Email(message = "Please enter a valid e-mail address.")
    private String email;

    @NotNull
    @Valid
    private AddressDto address;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\d{10}$")
    private String phoneNumber;
}
