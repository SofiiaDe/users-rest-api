package com.testtask.usersrestapi.validation;

import com.testtask.usersrestapi.utils.validation.DateRangeParameters;
import com.testtask.usersrestapi.utils.validation.validators.DateRangeValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateRangeValidatorTest {

    private static final LocalDate fromDate = LocalDate.of(1980, 1, 1);
    private static final LocalDate toDate = LocalDate.of(1996, 12, 31);
    private static final LocalDate fromDateNotValid = LocalDate.of(1996, 12, 31);
    private static final LocalDate toDateNotValid = LocalDate.of(1980, 1, 1);
    @InjectMocks
    private DateRangeValidator dateRangeValidator;

    private ConstraintValidatorContext constraintValidatorContext;

    ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @BeforeEach
    public void setUp() {
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
        constraintViolationBuilder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
    }

    @Test
    void isValidTest_ShouldReturnTrue_WhenPassValidDateRange() {
        DateRangeParameters dateRangeParameters = new DateRangeParameters(fromDate, toDate);

        assertTrue(dateRangeValidator.isValid(dateRangeParameters, constraintValidatorContext));
    }

    @Test
    void isValidTest_ShouldReturnFalse_WhenPassValidUserDto() {

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);
        DateRangeParameters dateRangeParameters = new DateRangeParameters(fromDateNotValid, toDateNotValid);

        assertFalse(dateRangeValidator.isValid(dateRangeParameters, constraintValidatorContext));
    }
}
