package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.exception.UserAlreadyExistsException;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.repository.UserRepository;
import com.testtask.usersrestapi.utils.IUserMapper;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final IUserMapper userMapper;

  private static final String USER_NOT_FOUND = "Can't retrieve user with id = ";
  private static final String USER_ALREADY_EXISTS = "There is an account with the following email address: ";

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(userMapper::userToDto)
        .toList();
  }

  @Transactional
  public UserDto getUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + id));

    return userMapper.userToDto(user);
  }

  @Transactional(rollbackOn = {Exception.class})
  public UserDto createUser(UserDto newUser) {
    if (emailExist(newUser.getEmail())) {
      throw new UserAlreadyExistsException(USER_ALREADY_EXISTS + newUser.getEmail());
    }

    return userMapper.userToDto(userRepository.save(userMapper.dtoToUser(newUser)));
  }

  private boolean emailExist(String email) {
    return userRepository.findUserByEmail(email).isPresent();
  }

}
