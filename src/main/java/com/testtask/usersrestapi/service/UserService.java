package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.action.params.AddUserToCommunityActionParams;
import com.testtask.usersrestapi.action.result.AddUserToCommunityActionExecutionResult;
import com.testtask.usersrestapi.exception.UserAlreadyExistsException;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.exception.UserProcessingException;
import com.testtask.usersrestapi.model.entity.User;
import com.testtask.usersrestapi.model.dto.UserDto;
import com.testtask.usersrestapi.model.entity.UserCommunity;
import com.testtask.usersrestapi.model.mapper.AddUserToGroupMapper;
import com.testtask.usersrestapi.model.mapper.UserMapper;
import com.testtask.usersrestapi.repository.UserGroupRepository;
import com.testtask.usersrestapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserMapper userMapper;
    private final AddUserToGroupMapper addUserToGroupMapper;
    private static final String USER_NOT_FOUND = "Can't retrieve user with id = ";
    private static final String USER_ALREADY_EXISTS = "There is an account with the following email address: ";
    private static final String CAN_NOT_DELETE_USER = "Can't delete user with id = ";

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + id));

        return userMapper.toDto(user);
    }

    @Override
    public UserDto createUser(UserDto newUser) {
        if (emailExist(newUser.getEmail())) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS + newUser.getEmail());
        }

        return userMapper.toDto(userRepository.save(userMapper.toEntity(newUser)));
    }

    @Override
    public UserDto updateUser(UserDto updatedUserDto) {

        return userMapper.toDto(userRepository.save(userMapper.toEntity(updatedUserDto)));
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception exception) {
            throw new UserProcessingException(CAN_NOT_DELETE_USER + id);
        }
    }

    @Override
    public UserDto patchUpdateUser(Map<String, Object> updates, Long id) {

        Optional<User> user = userRepository.findById(id);
        user.ifPresent(user1 -> updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user1, value);
        }));

        User updatedUser = userRepository.save(user.get());

        return userMapper.toDto(updatedUser);
    }

    @Override
    public List<UserDto> searchUsersByBirthDate(LocalDate fromDate, LocalDate toDate) {
        List<User> searchedUsers = userRepository.findByBirthDate(fromDate, toDate);
        return searchedUsers.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public AddUserToCommunityActionExecutionResult addUserToCommunity(
        AddUserToCommunityActionParams actionParams) {

        UserCommunity userCommunity = userGroupRepository.save(addUserToGroupMapper.toEntity(actionParams));
        UserDto userDto = getUserById(actionParams.getUserId());

        AddUserToCommunityActionExecutionResult result = new AddUserToCommunityActionExecutionResult()
            .setEmail(userDto.getEmail())
            .setUserFullName(userDto.getFirstName() + " " + userDto.getLastName())
            .setCommunityTitle(userCommunity.getCommunity().getTitle());
        result.setSuccess(true);

        return result;
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

}
