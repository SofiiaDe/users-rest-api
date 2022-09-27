package com.testtask.usersrestapi;

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
        userDto = UnitTestExpectedDtoSupplier.createUser();
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
        assertEquals(2, constraintViolations.size());
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
}
