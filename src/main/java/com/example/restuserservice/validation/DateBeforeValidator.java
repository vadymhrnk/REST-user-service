package com.example.restuserservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateBeforeValidator implements ConstraintValidator<DateBefore, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return true;
        }

        LocalDate currentDate = LocalDate.now();
        return date.isBefore(currentDate);
    }
}
