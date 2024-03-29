package com.testtask.usersrestapi.utils.validation.constraints;

import com.testtask.usersrestapi.utils.validation.validators.DateRangeValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRangeValidation {

    String message() default "`from` should be more recent then `to`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
