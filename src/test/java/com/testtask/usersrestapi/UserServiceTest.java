package com.testtask.usersrestapi;

import static com.testtask.usersrestapi.UnitTestExpectedEntitySupplier.createUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.testtask.usersrestapi.exception.UserAlreadyExistsException;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.exception.UserProcessingException;
import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.repository.UserRepository;
import com.testtask.usersrestapi.service.IUserService;
import com.testtask.usersrestapi.service.UserService;
import com.testtask.usersrestapi.utils.UserMapper;
import com.testtask.usersrestapi.utils.UserMapperImpl;

import java.util.HashMap;
import java.util.Map;
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
    private static final String CAN_NOT_DELETE_USER = "Can't delete user with id = ";
    private static final Long NOT_EXIST_ID = -1L;
    private static final Long DEFAULT_USER_ID = 123L;
    @Mock
    private UserRepository userRepositoryMock;
    private UserMapper userMapper;
    private IUserService userService;
    private UserDto userDto;
    private User expectedUser;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
        userService = new UserService(userRepositoryMock, userMapper);
        userDto = UnitTestExpectedDtoSupplier.createUser();
        expectedUser = UnitTestExpectedEntitySupplier.createUserEntity();
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
        User user = userMapper.dtoToUser(userDto);

        when(userRepositoryMock.findById(any())).thenReturn(Optional.of(user));

        userService.getUserById(DEFAULT_USER_ID);
        verify(userRepositoryMock, times(1)).findById(DEFAULT_USER_ID);
    }

    @Test
    void testGetUserById_whenRepoThrows_throwsException() {

        when(userRepositoryMock.findById(DEFAULT_USER_ID)).thenThrow(
                new UserNotFoundException(USER_NOT_FOUND + DEFAULT_USER_ID));

        assertThrowsExactly(UserNotFoundException.class, () -> userService.getUserById(DEFAULT_USER_ID));
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

        when(userRepositoryMock.findById(userId)).thenReturn(
                Optional.of(userMapper.dtoToUser(userDto)));

        UserDto result = userService.getUserById(userId);

        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getBirthDate(), result.getBirthDate());
        assertEquals(userDto.getAddress(), result.getAddress());
        assertEquals(userDto.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    void patchUpdateUserTest_ShouldUpdateSpecifiedFields() {

        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "newEmail@email.com");
        updates.put("address", "5th avenue");

        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expectedUser));

        UserDto actualDto = userService.patchUpdateUser(updates, expectedUser.getId());

        verify(userRepositoryMock).findById(expectedUser.getId());
        verify(userRepositoryMock).save(any(User.class));

        assertEquals(expectedUser.getEmail(), actualDto.getEmail());
        assertEquals(expectedUser.getAddress(), actualDto.getAddress());
    }


    @Test
    void deleteUserTest_WhenUserCanBeDeleted_RepoCalled() {
        userService.deleteUserById(DEFAULT_USER_ID);

        verify(userRepositoryMock, times(1)).deleteById(DEFAULT_USER_ID);
    }

    @Test
    void deleteUserTest_WhenUserNotFound_ShouldThrowException() {
        doThrow(UserNotFoundException.class)
                .when(userRepositoryMock)
                .deleteById(NOT_EXIST_ID);

        assertThrows(UserProcessingException.class, () -> userService.deleteUserById(NOT_EXIST_ID));
    }

    @Test
    void testDeleteUser_whenThrowsUserNotFoundException_shouldShowExceptionMessage() {
        String messageNotToGet = "aaaaa";
        doThrow(UserNotFoundException.class)
                .when(userRepositoryMock)
                .deleteById(NOT_EXIST_ID);

        UserProcessingException exception = assertThrows(UserProcessingException.class,
                () -> userService.deleteUserById(NOT_EXIST_ID));

        assertEquals(CAN_NOT_DELETE_USER + NOT_EXIST_ID, exception.getMessage());
        assertNotEquals(messageNotToGet, exception.getMessage());
    }
}
