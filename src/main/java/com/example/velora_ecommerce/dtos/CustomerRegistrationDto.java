package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.config.SecurityConfig;
import com.example.velora_ecommerce.entities.Address;
import com.example.velora_ecommerce.validation.PasswordConfirmation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import com.example.velora_ecommerce.validation.PasswordsMatch;

@Getter
@Setter
@NoArgsConstructor
@PasswordsMatch
public class CustomerRegistrationDto implements PasswordConfirmation {
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "E-mail is required.")
    @Email(message = "Please enter a valid e-mail address.")
    private String email;

    @NotNull
    @Valid
    private Address address = new Address();

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    @NotBlank(message = "Password is required.")
    @Size(min = 8,
            message = "Password must be at least 8 characters long.")
    @Pattern(regexp = SecurityConfig.REG_EXP,
            message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character.")
    private String password;

    @NotBlank(message = "Please confirm your password.")
    private String confirmPassword;
}
