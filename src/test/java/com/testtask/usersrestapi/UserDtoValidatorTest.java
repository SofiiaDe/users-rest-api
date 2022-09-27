package com.testtask.usersrestapi;

import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.utils.validation.validators.UserDtoValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.testtask.usersrestapi.UnitTestExpectedDtoSupplier.createUser;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserDtoValidatorTest {

    @InjectMocks
    private UserDtoValidator userDtoValidator;

    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    public void setUp() {
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @Test
    void isValidTest_ShouldReturnTrue_WhenPassValidUserDto() {
        UserDto userDto = createUser();
        boolean actual = userDtoValidator.isValid(new Object[]{userDto}, constraintValidatorContext);

        assertTrue(actual);
    }
}
