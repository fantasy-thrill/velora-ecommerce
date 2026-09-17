package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column(nullable = true)
    private String customerFirstName;

    @Column(nullable = true)
    private String customerLastName;

    @NotBlank
    @Column(nullable = false)
    private String street;

    @NotBlank
    @Column(nullable = false)
    private String city;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @NotBlank(message = "ZIP code is required.")
    @Pattern(
            regexp = "^\\d{5}(-\\d{4})?$",
            message = "Please enter a valid ZIP code."
    )
    @Column(nullable = false)
    private String zipCode;
}
