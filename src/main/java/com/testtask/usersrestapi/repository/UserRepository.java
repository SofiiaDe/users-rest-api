package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository{

  private List<User> list = new ArrayList<>();

  public void createUsers() {
    list.add(new User(1L, "smith@email.com", "Ron", "Smith",
        "1991-12-05", "Kyiv", "095-999-99-99"));
    list.add(new User(2L, "brown@email.com", "Kate", "Brown",
        "1996-09-17", "Warsaw", "044-444-44-44"));
  }

  @Override
  public List<User> findAll() {
    createUsers();
    return list;
  }

  @Override
  public Optional<User> findById(Long id) {
    User foundUser = new User();
    for (User user : list) {
      if (Objects.equals(user.getId(), id)) {
        foundUser = user;
      }
    }
    return Optional.of(foundUser); // Optional.ofNullable ???
  }

  @Override
  public User save(User newUser) {
    User user = new User();
    user.setId(newUser.getId());
    user.setEmail(newUser.getEmail());
    user.setFirstName(newUser.getFirstName());
    user.setLastName(newUser.getLastName());
    user.setBirthDate(newUser.getBirthDate());
    user.setAddress(newUser.getAddress());
    user.setPhoneNumber(newUser.getPhoneNumber());
    list.add(user);
    return user;
  }

  @Override
  public Optional<User> findUserByEmail(String email) {
    List<User> allUsers = findAll();
    return allUsers.stream().filter(user -> Objects.equals(user.getEmail(), email)).findFirst();
  }



}
