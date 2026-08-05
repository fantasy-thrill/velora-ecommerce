package com.example.velora_ecommerce.validation;

import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, PasswordConfirmation> {
    @Override
    public boolean isValid(PasswordConfirmation dto, ConstraintValidatorContext context) {
        if (dto == null) return true;
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) return false;

        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}