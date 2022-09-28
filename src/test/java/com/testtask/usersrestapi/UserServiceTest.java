package com.testtask.usersrestapi;

import com.testtask.usersrestapi.exception.UserAlreadyExistsException;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.exception.UserProcessingException;
import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.repository.IUserRepository;
import com.testtask.usersrestapi.service.IUserService;
import com.testtask.usersrestapi.service.UserService;
import com.testtask.usersrestapi.utils.UserMapper;
import com.testtask.usersrestapi.utils.UserMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String USER_ALREADY_EXISTS = "There is an account with the following email address: ";
    private static final String USER_NOT_FOUND = "Can't retrieve user with id = ";
    private static final String CAN_NOT_DELETE_USER = "Can't delete user with id = ";
    private static final Long NOT_EXIST_ID = -1L;
    private static final Long DEFAULT_USER_ID = 123L;
    private static final LocalDate fromDate = LocalDate.of(1980, 1, 1);
    private static final LocalDate toDate = LocalDate.of(1996, 12, 31);
    @Mock
    private IUserRepository userRepositoryMock;
    private UserMapper userMapper;
    private IUserService userService;
    private UserDto userDto;
    private User expectedUser;
    private List<UserDto> userDtoList;
    private List<User> usersList;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
        userService = new UserService(userRepositoryMock, userMapper);
        userDto = UnitTestExpectedDtoSupplier.createUserDto();
        expectedUser = UnitTestExpectedEntitySupplier.createUserEntity();
        userDtoList = UnitTestExpectedDtoSupplier.createUserDtoList();
        usersList = UnitTestExpectedEntitySupplier.createUsersList();
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
        when(userRepositoryMock.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        UserDto actualDto = userService.patchUpdateUser(updates, expectedUser.getId());

        verify(userRepositoryMock).findById(expectedUser.getId());
        verify(userRepositoryMock).save(any(User.class));

        assertEquals(updates.get("email"), actualDto.getEmail());
        assertEquals(updates.get("address"), actualDto.getAddress());
    }

    @Test
    void testDeleteUser_WhenUserCanBeDeleted_RepoCalled() {
        userService.deleteUserById(DEFAULT_USER_ID);

        verify(userRepositoryMock, times(1)).deleteById(DEFAULT_USER_ID);
    }

    @Test
    void testDeleteUser_WhenUserNotFound_ShouldThrowException() {
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

    @Test
    void testSearchUsersByBirthDate_whenNoUsersFound_returnsEmptyCollections() {
        when(userRepositoryMock.findByBirthDate(any(), any())).thenReturn(Collections.emptyList());

        List<UserDto> result = userService.searchUsersByBirthDate(fromDate, toDate);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchUsersByBirthDate_whenCalled_repositoryCalled() {

        userService.searchUsersByBirthDate(fromDate, toDate);
        verify(userRepositoryMock, times(1)).findByBirthDate(fromDate, toDate);
    }

    @Test
    void testSearchUsersByBirthDate_whenCalled_returnCorrectData() {

        when(userRepositoryMock.findByBirthDate(fromDate, toDate)).thenReturn(usersList);

        List<UserDto> result = userService.searchUsersByBirthDate(fromDate, toDate);

        assertEquals(result, userDtoList);
    }

    @Test
    void testSearchUsersByBirthDate_whenCalled_shouldFilterData() {

        List<User> allUsers = new ArrayList<>();
        User userWithBoundaryBirthDate = new User(3L, "email@email.com", "FirstName", "LastName",
                LocalDate.of(1995, 1, 1), "Kyiv", "095-999-99-99");
        allUsers.add(usersList.get(0));
        allUsers.add(usersList.get(1));
        allUsers.add(expectedUser);
        allUsers.add(userWithBoundaryBirthDate);

        List<User> expectedResult = List.of(usersList.get(1), userWithBoundaryBirthDate);

        LocalDate newFromDate = LocalDate.of(1995, 1, 1);
        when(userRepositoryMock.findByBirthDate(newFromDate, toDate)).thenReturn(expectedResult);
        List<UserDto> actualResult = userService.searchUsersByBirthDate(newFromDate, toDate);

        assertThat(actualResult, hasSize(2));
        assertThat(actualResult.get(0).getBirthDate(), is(LocalDate.of(1996, 9, 17)));
        assertThat(actualResult.get(1).getBirthDate(), is(newFromDate));

    }
}
