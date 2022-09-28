package com.testtask.usersrestapi.utils.validation.constraints;

import com.testtask.usersrestapi.utils.validation.validators.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRangeValidation {

    String message() default "`from` should be more recent then `to`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
