package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.action.params.AddUserToCommunityActionParams;
import com.testtask.usersrestapi.action.result.AddUserToCommunityActionExecutionResult;
import com.testtask.usersrestapi.model.dto.UserDto;

import com.testtask.usersrestapi.model.payload.request.SearchRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface IUserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto createUser(UserDto newUser);

    UserDto updateUser(UserDto userDto);

    void deleteUserById(Long id);

    UserDto patchUpdateUser(Map<String, Object> updates, Long id);

    List<UserDto> searchUsersByBirthDate(LocalDate fromDate, LocalDate toDate);

    Page<UserDto> searchUser(SearchRequest request);

    AddUserToCommunityActionExecutionResult addUserToCommunity(AddUserToCommunityActionParams actionParams);
}
