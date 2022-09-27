package com.testtask.usersrestapi.utils.validation.validators;

import com.testtask.usersrestapi.utils.validation.constraints.UserDtoValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.springframework.stereotype.Component;

@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class UserDtoValidator implements ConstraintValidator<UserDtoValidation, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return true;
    }
}
