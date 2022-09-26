package com.testtask.usersrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.testtask.usersrestapi.controller.UserController;
import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.model.UserModelAssembler;
import com.testtask.usersrestapi.service.IUserService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    private static final String USER_ENDPOINT = "/usersApi/users";
    private static final String UPDATE_USER_ENDPOINT = "/usersApi/users" + "/{id}";
    private static final long DEFAULT_USER_ID = 123L;
    private static final Long NOT_EXIST_ID = -1L;
    private MockMvc mockMvc;
    @Mock
    private IUserService userService;
    @InjectMocks
    private UserController userController;
    @Spy
    private UserModelAssembler userModelAssembler;
    private UserDto userDto;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userDto = UnitTestExpectedDtoSupplier.createUser();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getUserByIdTest_givenExistingUser_whenUserRequested_thenResourceRetrieved() throws Exception {
        given(this.userService.getUserById(DEFAULT_USER_ID))
                .willReturn(UnitTestExpectedDtoSupplier.createUser());

        this.mockMvc.perform(get(USER_ENDPOINT + "/" + DEFAULT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").doesNotExist())
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    void getAllUsersTest() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(UnitTestExpectedDtoSupplier.createUsersList());

        mockMvc
                .perform(
                        get(USER_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    void updateUserTest_ShouldUpdateSpecificUserData() throws Exception {
        UserDto requestUserDto = userDto
                .setEmail("newEmailForUpdate@email.com")
                .setAddress("Odesa");

        UserDto responseUserDto = userDto;

        when(userService.updateUser(requestUserDto, requestUserDto.getId())).thenReturn(responseUserDto);

        mockMvc.perform(
                        put(UPDATE_USER_ENDPOINT, DEFAULT_USER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("newEmailForUpdate@email.com")))
                .andExpect(jsonPath("$.address", is("Odesa")))
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.firstName", is("UserFirstName")))
                .andExpect(jsonPath("$.lastName", is("UserLastName")))
                .andExpect(jsonPath("$.birthDate", is("1991-07-25")))
                .andExpect(jsonPath("$.phoneNumber", is("099-999-99-99")));
    }


}
