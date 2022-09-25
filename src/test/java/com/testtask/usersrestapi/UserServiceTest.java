package com.testtask.usersrestapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testtask.usersrestapi.exception.UserAlreadyExistsException;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.repository.UserRepository;
import com.testtask.usersrestapi.service.IUserService;
import com.testtask.usersrestapi.service.UserService;
import com.testtask.usersrestapi.utils.UserMapper;
import com.testtask.usersrestapi.utils.UserMapperImpl;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String USER_ALREADY_EXISTS = "There is an account with the following email address: ";
    private static final String USER_NOT_FOUND = "Can't retrieve user with id = ";
    @Mock
    private UserRepository userRepositoryMock;
    private UserMapper userMapper;
    private IUserService userService;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
        userService = new UserService(userRepositoryMock, userMapper);
        userDto = UnitTestExpectedDtoSupplier.createUser();
    }

    @Test
    void testGetAllUsers_whenCalled_callsRepo() {

        userService.getAllUsers();
        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    void testCreateUser_whenRepoThrows_throwsException() {
        when(userRepositoryMock.findUserByEmail(userDto.getEmail())).thenReturn(
                Optional.of(userMapper.dtoToUser(userDto)));

        assertThrowsExactly(UserAlreadyExistsException.class, () -> userService.createUser(userDto));
    }

    @Test
    void testCreateUser_whenThrowsUserExistsException_shouldShowExceptionMessage() {
        String messageNotToGet = "aaaaa";
        when(userRepositoryMock.findUserByEmail(userDto.getEmail())).thenReturn(
                Optional.of(userMapper.dtoToUser(userDto)));

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(userDto));

        assertEquals(USER_ALREADY_EXISTS + userDto.getEmail(), exception.getMessage());
        assertNotEquals(messageNotToGet, exception.getMessage());

    }

    @Test
    void testCreateUser_whenCalled_callsRepo() throws UserAlreadyExistsException {

        userService.createUser(new UserDto());
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById_whenCalled_RepoCalled() {
        long userId = 1125L;
        User user = userMapper.dtoToUser(userDto);

        when(userRepositoryMock.findById(any())).thenReturn(Optional.of(user));

        userService.getUserById(userId);
        verify(userRepositoryMock, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_whenRepoThrows_throwsException() {
        long userId = 1125L;

        when(userRepositoryMock.findById(userId)).thenThrow(
                new UserNotFoundException(USER_NOT_FOUND + userId));

        assertThrowsExactly(UserNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testGetUserById_whenThrowsNotFoundException_ShouldShowExceptionMessage() {
        String messageNotToGet = "aaaaa";
        long userId = 1125L;

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(userId));
        assertEquals(USER_NOT_FOUND + userId, exception.getMessage());
        assertNotEquals(messageNotToGet, exception.getMessage());

    }

    @Test
    void testGetUserById_whenCalled_returnsCorrectUser() {
        long userId = 123L;
        UserDto expectedUser = UnitTestExpectedDtoSupplier.createUser();

        when(userRepositoryMock.findById(userId)).thenReturn(
                Optional.of(userMapper.dtoToUser(expectedUser)));

        UserDto result = userService.getUserById(userId);

        assertEquals(expectedUser.getId(), result.getId());
        assertEquals(expectedUser.getEmail(), result.getEmail());
        assertEquals(expectedUser.getFirstName(), result.getFirstName());
        assertEquals(expectedUser.getLastName(), result.getLastName());
        assertEquals(expectedUser.getBirthDate(), result.getBirthDate());
        assertEquals(expectedUser.getAddress(), result.getAddress());
        assertEquals(expectedUser.getPhoneNumber(), result.getPhoneNumber());
    }


}
