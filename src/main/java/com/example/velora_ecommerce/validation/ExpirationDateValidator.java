package com.example.velora_ecommerce.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpirationDateValidator implements ConstraintValidator<FutureOrPast, PaymentCardExpiration> {
    @Override
    public boolean isValid(PaymentCardExpiration dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        Integer month = dto.getExpirationMonth();
        Integer year = dto.getExpirationYear();

        if (month == null || year == null) return true;

        LocalDate currentDate = LocalDate.now();
        LocalDate enteredExpiration = YearMonth.of(year, month).atEndOfMonth();

        return !enteredExpiration.isBefore(currentDate);
    }
}
