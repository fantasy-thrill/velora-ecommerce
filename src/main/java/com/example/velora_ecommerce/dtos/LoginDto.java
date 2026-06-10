package com.example.velora_ecommerce.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "E-mail is required.")
    @Email(message = "Please enter a valid e-mail address.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
