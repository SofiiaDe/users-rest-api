package com.testtask.usersrestapi.utils.validation.validators;

import com.testtask.usersrestapi.utils.validation.constraints.UserDtoValidation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import org.springframework.stereotype.Component;

@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class UserDtoValidator implements ConstraintValidator<UserDtoValidation, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return true;
    }
}
