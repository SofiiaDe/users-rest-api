package com.testtask.usersrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.testtask.usersrestapi.controller.UserController;
import com.testtask.usersrestapi.exception.UserProcessingException;
import com.testtask.usersrestapi.model.dto.UserDto;
import com.testtask.usersrestapi.model.UserModelAssembler;
import com.testtask.usersrestapi.service.IUserService;
import com.testtask.usersrestapi.utils.validation.DateRangeParameters;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.testtask.usersrestapi.UnitTestExpectedDtoSupplier.createUserDtoList;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    private static final String USER_ENDPOINT = "/users";
    private static final String USER_ENDPOINT_ID = USER_ENDPOINT + "/{id}";
    private static final String SEARCH_USER_ENDPOINT = USER_ENDPOINT + "/search";
    private static final Long DEFAULT_USER_ID = 123L;
    private static final Long NOT_EXIST_ID = -1L;
    private static final LocalDate fromDate = LocalDate.of(1990, 1, 1);
    private static final LocalDate toDate = LocalDate.of(1996, 12, 31);
    private MockMvc mockMvc;
    @Mock
    private IUserService userService;
    @InjectMocks
    private UserController userController;
    @Spy
    private UserModelAssembler userModelAssembler;
    private UserDto userDto;
    private ObjectMapper objectMapper;
    private List<UserDto> userDtoList;
    private DateRangeParameters dateRangeParams;

    private String jsonUser;

    @BeforeEach
    public void setUp() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userDto = UnitTestExpectedDtoSupplier.createUserDto();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        userDtoList = createUserDtoList();
        dateRangeParams = new DateRangeParameters(fromDate, toDate);
        jsonUser = readJsonWithFile();

    }

    @Test
    void getUserByIdTest_givenExistingUser_whenUserRequested_thenResourceRetrieved() throws Exception {
        given(this.userService.getUserById(DEFAULT_USER_ID))
                .willReturn(UnitTestExpectedDtoSupplier.createUserDto());

        this.mockMvc.perform(get(USER_ENDPOINT + "/" + DEFAULT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").doesNotExist())
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    void getAllUsersTest() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(createUserDtoList());

        mockMvc
                .perform(
                        get(USER_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    void createUserTest_shouldInvokeServiceMethodCreateUser() throws Exception {
        when(userService.createUser(any())).thenReturn(userDto);

        mockMvc.perform(
                        post(USER_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUser))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void updateUserTest_ShouldUpdateAllUserData() throws Exception {
        UserDto requestUserDto = userDto
                .setId(12L)
                .setEmail("newEmailForUpdate@email.com")
                .setFirstName("newFirstName")
                .setLastName("newLastName")
                .setBirthDate(LocalDate.of(1992, 7, 25))
                .setAddress("Odesa")
                .setPhoneNumber("099-000-99-99");

        UserDto responseUserDto = userDto;
        when(userService.updateUser(requestUserDto)).thenReturn(responseUserDto);

        mockMvc.perform(
                        put(USER_ENDPOINT, requestUserDto)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("newEmailForUpdate@email.com")))
                .andExpect(jsonPath("$.address", is("Odesa")))
                .andExpect(jsonPath("$.id", is(12)))
                .andExpect(jsonPath("$.firstName", is("newFirstName")))
                .andExpect(jsonPath("$.lastName", is("newLastName")))
                .andExpect(jsonPath("$.birthDate", is("1992-07-25")))
                .andExpect(jsonPath("$.phoneNumber", is("099-000-99-99")));
    }

    @Test
    void partialUpdateUserTest_ShouldUpdateSpecificUserData() throws Exception {

        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "newEmail@email.com");
        updates.put("address", "5th avenue");

        when(userService.patchUpdateUser(updates, userDto.getId())).thenReturn(userDto);

        mockMvc.perform(
                        patch(USER_ENDPOINT_ID, DEFAULT_USER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserTest_WhenFound() throws Exception {
        doNothing().when(userService).deleteUserById(DEFAULT_USER_ID);

        mockMvc
                .perform(delete(USER_ENDPOINT + "/{id}", DEFAULT_USER_ID))
                .andExpect(status().is2xxSuccessful());

        verify(userService).deleteUserById(DEFAULT_USER_ID);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void deleteUser_WhenNotFound() throws Exception {
        doThrow(new UserProcessingException("some message")).when(userService).deleteUserById(NOT_EXIST_ID);

        mockMvc
                .perform(delete(USER_ENDPOINT + "/{id}", NOT_EXIST_ID))
                .andExpect(status().isBadRequest());
        verify(userService).deleteUserById(NOT_EXIST_ID);
    }

    private String readJsonWithFile() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("UserJSON.json");
        assert inputStream != null;
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

}
