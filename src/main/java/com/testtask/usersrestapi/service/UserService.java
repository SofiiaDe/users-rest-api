package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.exception.UserAlreadyExistsException;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.exception.UserProcessingException;
import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.repository.UserRepository;
import com.testtask.usersrestapi.utils.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final String USER_NOT_FOUND = "Can't retrieve user with id = ";
    private static final String USER_ALREADY_EXISTS = "There is an account with the following email address: ";
    private static final String CAN_NOT_DELETE_USER = "Can't delete user with id = ";

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + id));

        return userMapper.userToDto(user);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public UserDto createUser(UserDto newUser) {
        if (emailExist(newUser.getEmail())) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS + newUser.getEmail());
        }

        return userMapper.userToDto(userRepository.save(userMapper.dtoToUser(newUser)));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto updatedUserDto) {

        return userMapper.userToDto(userRepository.save(userMapper.dtoToUser(updatedUserDto)));
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
        User userAfterUpdate = user.get();

        User updatedUser = userRepository.save(userAfterUpdate);

        return userMapper.userToDto(updatedUser);
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

}
