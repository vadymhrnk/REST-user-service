package com.example.restuserservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.beans.factory.annotation.Value;

public class AgeValidator implements ConstraintValidator<AgeMatch, LocalDate> {
    @Value("${minimal.user.age}")
    private int minimalAge;

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return true;
        }

        formatMessage(constraintValidatorContext);

        LocalDate dateNow = LocalDate.now();
        Period period = Period.between(date, dateNow);
        return period.getYears() >= minimalAge;
    }

    private void formatMessage(ConstraintValidatorContext context) {
        String messageTemplate = context.getDefaultConstraintMessageTemplate();
        String format = String.format(messageTemplate, this.minimalAge);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(format)
                .addConstraintViolation();
    }
}
