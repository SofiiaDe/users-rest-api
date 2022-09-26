package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.model.UserDto;

import java.util.List;

public interface IUserService {

  List<UserDto> getAllUsers();

  UserDto getUserById(Long id);

  UserDto createUser(UserDto newUser);

  UserDto updateUser(UserDto userDto, Long id);

  void deleteUserById(Long id);
}
