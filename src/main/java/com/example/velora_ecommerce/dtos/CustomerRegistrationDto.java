package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.config.SecurityConfig;
import jakarta.validation.constraints.*;
import lombok.*;
import com.example.velora_ecommerce.validation.PasswordsMatch;

@Getter
@Setter
@NoArgsConstructor
@PasswordsMatch
public class CustomerRegistrationDto {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "E-mail is required.")
    @Email(message = "Please enter a valid e-mail address.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8,
            message = "Password must be at least 8 characters long.")
    @Pattern(regexp = SecurityConfig.REG_EXP,
            message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character.")
    private String password;

    @NotBlank(message = "Please confirm your password.")
    private String confirmPassword;

    @NotBlank(message = "Street address is required")
    private String streetAddress;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;
}
