package com.testtask.usersrestapi.utils.validation.constraints;

import com.testtask.usersrestapi.utils.validation.validators.BirthDateValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = BirthDateValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface BirthDateValidation {

    String message() default "{com.testtask.usersrestapi.utils.validation.constraints.BirthDate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

