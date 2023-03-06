package com.testtask.usersrestapi.validation;

import com.testtask.usersrestapi.utils.validation.DateRangeParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DateRangeValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("notValidDateRange")
    void isValidTest_WhenPassNotValidRange_ThenIsViolationConstraint(DateRangeParameters dateRangeParameters) {
        assertEquals(1, validator.validate(dateRangeParameters).size());
    }

    private static Stream<Arguments> notValidDateRange() {
        return Stream.of(
                arguments(new DateRangeParameters(LocalDate.of(1990, 1, 1),
                        LocalDate.of(1986, 12, 31))),
                arguments(new DateRangeParameters(LocalDate.of(2000, 1, 31),
                        LocalDate.of(2000, 1, 30))),
                arguments(new DateRangeParameters(LocalDate.of(1980, 1, 1),
                        LocalDate.of(1976, 12, 31)))
        );
    }

    @ParameterizedTest
    @MethodSource("validDateRange")
    void isValidTest_WhenPassValidRange_ThenNoViolationConstraint(DateRangeParameters dateRangeParameters) {
        assertEquals(0, validator.validate(dateRangeParameters).size());
    }

    private static Stream<Arguments> validDateRange() {
        return Stream.of(
                arguments(new DateRangeParameters(LocalDate.of(1986, 1, 30),
                        LocalDate.of(1986, 12, 31))),
                arguments(new DateRangeParameters(LocalDate.of(2000, 1, 31),
                        LocalDate.of(2001, 1, 30))),
                arguments(new DateRangeParameters(LocalDate.of(1980, 1, 1),
                        LocalDate.of(1980, 2, 1))),
                arguments(new DateRangeParameters(LocalDate.of(1990, 1, 1),
                        LocalDate.of(1990, 1, 1)))
        );
    }

}