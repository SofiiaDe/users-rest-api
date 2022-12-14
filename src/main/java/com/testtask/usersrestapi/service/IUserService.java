package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.model.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IUserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto createUser(UserDto newUser);

    UserDto updateUser(UserDto userDto);

    void deleteUserById(Long id);

    UserDto patchUpdateUser(Map<String, Object> updates, Long id);

    List<UserDto> searchUsersByBirthDate(LocalDate fromDate, LocalDate toDate);
}
