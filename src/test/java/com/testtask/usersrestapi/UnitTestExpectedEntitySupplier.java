package com.testtask.usersrestapi;

import com.testtask.usersrestapi.model.entity.User;

import java.time.LocalDate;
import java.util.List;

public class UnitTestExpectedEntitySupplier {

    public static User createUserEntity() {
        return new User()
                .setId(123L)
                .setEmail("aaa@bbb.ccc")
                .setFirstName("UserFirstName")
                .setLastName("UserLastName")
                .setBirthDate(LocalDate.of(1991, 7, 25))
                .setAddress("Kyiv")
                .setPhoneNumber("099-999-99-99");
    }

    public static List<User> createUsersList() {

        return List.of(
                new User(1L, "smith@email.com", "Ron", "Smith",
                        LocalDate.of(1991, 12, 5), "Kyiv", "095-999-99-99"),

                new User(2L, "brown@email.com", "Kate", "Brown",
                        LocalDate.of(1996, 9, 17), "Warsaw", "044-444-44-44"));
    }
}
