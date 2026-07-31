package com.example.velora_ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ForgotPasswordEmailDto {
    @Email
    @NotBlank
    private String email;
}
