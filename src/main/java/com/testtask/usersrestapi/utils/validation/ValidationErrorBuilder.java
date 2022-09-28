package com.testtask.usersrestapi.utils.validation;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Set;

public class ValidationErrorBuilder {

    private ValidationErrorBuilder() {
    }

    public static ValidationError fromBindingErrors(Set<ConstraintViolation<?>> violations) {
        ValidationError error = new ValidationError();
        error.setErrorMessage("Validation failed. " + violations.size() + " error(s)");

        for (ConstraintViolation<?> violation : violations) {
            String validatorClassName = violation.getConstraintDescriptor()
                    .getConstraintValidatorClasses().get(0).getName();
            error.addValidationError(validatorClassName, violation.getMessage());
        }

        return error;
    }

    public static ValidationError fromBindingResult(BindingResult bindingResult) {
        ValidationError error = new ValidationError();
        error.setErrorMessage("Validation failed. " + bindingResult.getErrorCount() + " error(s)");

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            error.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return error;
    }
}
