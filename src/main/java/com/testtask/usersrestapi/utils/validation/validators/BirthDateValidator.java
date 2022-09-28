package com.testtask.usersrestapi.utils.validation.validators;

import com.testtask.usersrestapi.utils.validation.constraints.BirthDateValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BirthDateValidator implements ConstraintValidator<BirthDateValidation, LocalDate> {

    @Value("${user.min.age}")
    private int userMinAge;

    @Override
    public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {

        return valueToValidate != null && LocalDate.now().getYear() - valueToValidate.getYear() >= userMinAge;
    }
}
