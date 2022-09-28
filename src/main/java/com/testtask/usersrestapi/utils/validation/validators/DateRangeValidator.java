package com.testtask.usersrestapi.utils.validation.validators;

import com.testtask.usersrestapi.utils.validation.DateRangeParameters;
import com.testtask.usersrestapi.utils.validation.constraints.DateRangeValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRangeValidation, DateRangeParameters> {
    @Override
    public boolean isValid(DateRangeParameters value,
                           ConstraintValidatorContext context) {
        if (value.from().isAfter(value.to())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format("From (%s) is after to (%s), which is invalid.", value.from(), value.to()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
