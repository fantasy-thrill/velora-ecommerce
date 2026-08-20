package com.example.velora_ecommerce.dtos;

import com.example.velora_ecommerce.config.SecurityConfig;
import com.example.velora_ecommerce.validation.PasswordConfirmation;
import jakarta.validation.constraints.*;
import lombok.*;
import com.example.velora_ecommerce.validation.PasswordsMatch;

@Getter
@Setter
@NoArgsConstructor
@PasswordsMatch
public class ChangePasswordDto implements PasswordConfirmation {
    private String currentPassword;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern(regexp = SecurityConfig.REG_EXP,
            message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character.")
    private String password;

    @NotBlank(message = "Please confirm your password.")
    private String confirmPassword;
}
