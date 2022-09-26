package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.model.UserDto;

import javax.transaction.Transactional;
import java.util.List;

public interface IUserService {

  List<UserDto> getAllUsers();

  UserDto getUserById(Long id);

  UserDto createUser(UserDto newUser);

  UserDto updateUser(UserDto userDto, Long id);
}
