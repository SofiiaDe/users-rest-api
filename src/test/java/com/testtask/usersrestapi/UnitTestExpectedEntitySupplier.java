package com.testtask.usersrestapi;

import com.testtask.usersrestapi.model.User;

import java.time.LocalDate;

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
}
