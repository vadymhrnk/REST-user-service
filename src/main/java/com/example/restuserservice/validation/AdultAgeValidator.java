package com.example.restuserservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.beans.factory.annotation.Value;

public class AdultAgeValidator implements ConstraintValidator<AgeMatch, LocalDate> {
    @Value("${minimal.user.age}")
    private int minimalAge;

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate dateNow = LocalDate.now();
        Period period = Period.between(date, dateNow);
        return period.getYears() >= minimalAge;
    }
}
