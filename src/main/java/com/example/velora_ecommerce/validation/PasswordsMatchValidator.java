package com.example.velora_ecommerce.validation;

import com.example.velora_ecommerce.dtos.CustomerRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator
        implements ConstraintValidator<PasswordsMatch, CustomerRegistrationDto> {

    @Override
    public boolean isValid(
            CustomerRegistrationDto dto,
            ConstraintValidatorContext context) {

        if (dto == null) {
            return true;
        }

        return dto.getPassword()
                .equals(dto.getConfirmPassword());
    }
}