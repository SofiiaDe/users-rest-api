package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.action.params.AddUserToCommunityActionParams;
import com.testtask.usersrestapi.action.result.AddUserToCommunityActionExecutionResult;
import com.testtask.usersrestapi.exception.UserAlreadyExistsException;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.exception.UserProcessingException;
import com.testtask.usersrestapi.model.entity.Community;
import com.testtask.usersrestapi.model.entity.User;
import com.testtask.usersrestapi.model.dto.UserDto;
import com.testtask.usersrestapi.model.entity.UserCommunity;
import com.testtask.usersrestapi.model.mapper.AddUserToGroupMapper;
import com.testtask.usersrestapi.model.mapper.UserMapper;
import com.testtask.usersrestapi.model.payload.request.SearchRequest;
import com.testtask.usersrestapi.model.search.SearchSpecification;
import com.testtask.usersrestapi.repository.IUserSearchRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.testtask.usersrestapi.repository.CommunityRepository;
import com.testtask.usersrestapi.repository.UserGroupRepository;
import com.testtask.usersrestapi.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final CommunityRepository communityRepository;
    private final IUserSearchRepository userSearchRepository;
    private final UserMapper userMapper;
    private final AddUserToGroupMapper addUserToGroupMapper;
    private static final String USER_NOT_FOUND = "Can't retrieve user with id = ";
    private static final String COMMUNITY_NOT_FOUND = "Can't retrieve community with id = ";
    private static final String USER_ALREADY_EXISTS = "There is an account with the following email address: ";
    private static final String CAN_NOT_DELETE_USER = "Can't delete user with id = ";
    private static final String UPDATE_EXCEPTION = "Can't update the user";


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
        User user;
        if (userRepository.existsById(updatedUserDto.getId())) {
            User userToBeUpdated = userMapper.toEntity(updatedUserDto);
            user = userRepository.save(userToBeUpdated);
        } else {
            log.error(UPDATE_EXCEPTION);
            throw new UserProcessingException(UPDATE_EXCEPTION);
        }
        return userMapper.toDto(user);
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
            switch (key) {
                case "email" -> user1.setEmail((String) value);
                case "firstName" -> user1.setFirstName((String) value);
                case "lastName" -> user1.setLastName((String) value);
                case "birthDate" ->
                        user1.setBirthDate(LocalDate.parse((String) value, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                case "address" -> user1.setAddress((String) value);
                case "phoneNumber" -> user1.setPhoneNumber((String) value);
                default -> {
                }
            }
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

        User user = userRepository.findById(actionParams.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + actionParams.getUserId()));
        Community community = communityRepository.findById(actionParams.getCommunityId())
                .orElseThrow(() -> new UserNotFoundException(COMMUNITY_NOT_FOUND + actionParams.getCommunityId()));
        UserCommunity userCommunity = userGroupRepository.save(
                new UserCommunity().setUser(user).setCommunity(community));

        AddUserToCommunityActionExecutionResult result = new AddUserToCommunityActionExecutionResult()
                .setEmail(user.getEmail())
                .setUserFullName(user.getFirstName() + " " + user.getLastName())
                .setCommunityTitle(userCommunity.getCommunity().getTitle());
        result.setSuccess(true);

        return result;
    }

    @Override
    public Page<UserDto> searchUser(SearchRequest request) {
        SearchSpecification<User> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return userSearchRepository.findAll(specification, pageable).map(userMapper::toDto);
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

}
