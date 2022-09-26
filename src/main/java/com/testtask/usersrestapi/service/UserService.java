package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.exception.UserAlreadyExistsException;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.exception.UserProcessingException;
import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.repository.UserRepository;
import com.testtask.usersrestapi.utils.UserMapper;

import java.util.List;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public UserDto updateUser(UserDto newUserDto, Long id) {
        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setEmail(newUserDto.getEmail());
                    user.setFirstName(newUserDto.getFirstName());
                    user.setLastName(newUserDto.getLastName());
                    user.setBirthDate(newUserDto.getBirthDate());
                    user.setAddress(newUserDto.getAddress());
                    user.setPhoneNumber(newUserDto.getPhoneNumber());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUserDto.setId(id);
                    return userRepository.save(userMapper.dtoToUser(newUserDto));
                });
        return userMapper.userToDto(updatedUser);
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception exception) {
            throw new UserProcessingException(CAN_NOT_DELETE_USER + id);
        }
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

}
