package com.testtask.usersrestapi.validation;

import com.testtask.usersrestapi.UnitTestExpectedDtoSupplier;
import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.utils.validation.validators.BirthDateValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = {"user.min.age=18"}, classes = BirthDateValidator.class)
@ExtendWith(MockitoExtension.class)
public class UserDtoValidationTest {

    public static final String INVALID_EMAIL = "ssdsd@@gmail.com";
    public static final String INVALID_PHONE = "099-999-99-99";
    private static final LocalDate LESS_THAN_MIN_AGE = LocalDate.now();
    private static Validator validator;
    private UserDto userDto;

    @Value("${user.min.age}")
    public int minAge;

    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setUp() {
        userDto = UnitTestExpectedDtoSupplier.createUserDto();
    }

    @Test
    void whenFilePropertyProvided_thenProperlyInjected() {
        assertEquals(18, minAge);
    }


    @Test
    void isValidUserDtoTest_WhenUserDtoIsNull_ThenInvalidUserDto() {
        userDto.setId(null);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    void isValidUserDtoTest_WhenUserDtoEmailNotValid_ThenInvalidUserDto() {
        userDto.setEmail(INVALID_EMAIL);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    void isValidUserDtoTest_WhenUserDtoFirstNameIsNull_ThenInvalidUserDto() {
        userDto.setFirstName(null);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    void isValidUserDtoTest_WhenUserDtoLastNameIsNull_ThenInvalidUserDto() {
        userDto.setLastName(null);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    void isValidUserDtoTest_WhenUserDtoBirthDateNotValid_ThenInvalidUserDto() {
        userDto.setBirthDate(LESS_THAN_MIN_AGE);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    void isValidUserDtoTest_WhenUserDtoBirthDateIsNull_ThenInvalidUserDto() {
        userDto.setBirthDate(null);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(2, constraintViolations.size());
    }

    @Test
    void isValidUserDtoTest_WhenUserDtoAddressIsNull_ThenValidUserDto() {
        userDto.setAddress(null);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    void isValidUserDtoTest_WhenUserDtoPhoneNotValid_ThenInValidUserDto() {
        userDto.setPhoneNumber(INVALID_PHONE);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    void isValidUserDtoTest_WhenUserDtoPhoneIsNull_ThenValidUserDto() {
        userDto.setPhoneNumber(null);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    void userDtoWithValidEmailAndId() {
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(0, constraintViolations.size());
    }

    @ParameterizedTest(name = "{index} \"{0}\" is not a valid email")
    @ValueSource(strings = {
            "",
            " fast.mary@hot.com",
            "ann.flex@gmail@com",
            "alice.example.com",
            "ron.gimmy@@com",
            "william...shakespeare@ukr.net",
            "bob @ukr.net",
            ".shakespeare123@hotmail.com",
            "hello",
            "tanya.@example.com",
            "...@test.com",
            "@com.a",
            "roma@example..com",
            "jade@.com",
            "garry@.com.",
            "polina@-gmail.com",

    })
    void testIsEmailValid_InvalidCases(String input) {
        userDto.setEmail(input);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(1, constraintViolations.size());
    }

    @ParameterizedTest(name = "{index} \"{0}\" is a valid email")
    @ValueSource(strings = {
            "tony@example.co.uk",
            "william_shakespeare1@epam.com",
            "william@gmail.com",
            "william.adam3@yahoo.com",
            "_karamel@hotmail.com",
            "william-shakespeare@gmail1.com",
            "hello.ann-2022@test.com",
            "boyko_denys@example.com",
            "h@hotmail.com",
            "h@example-example.com",
            "h@test-test-test.com",
            "h@test.tset-test.com",
            "hello.mary-2022@example.com",

    })
    void testIsEmailValid_ValidCases(String input) {
        userDto.setEmail(input);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        assertEquals(0, constraintViolations.size());
    }

}

