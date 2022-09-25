package com.testtask.usersrestapi;

import com.testtask.usersrestapi.model.UserDto;

import java.util.List;

public class UnitTestExpectedDtoSupplier {

    public static UserDto createUser() {
        return new UserDto()
                .setId(123L)
                .setEmail("aaa@bbb.ccc")
                .setFirstName("UserFirstName")
                .setLastName("UserLastName")
                .setBirthDate("1991-07-25")
                .setAddress("Kyiv")
                .setPhoneNumber("099-999-99-99");
    }

    public static List<UserDto> createUsersList() {

        return List.of(
                new UserDto(1L, "smith@email.com", "Ron", "Smith",
                        "1991-12-05", "Kyiv", "095-999-99-99"),

                new UserDto(2L, "brown@email.com", "Kate", "Brown",
                        "1996-09-17", "Warsaw", "044-444-44-44"));
    }
}
