package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {

    private final List<User> list;

    /**
     * Imitates database
     */
    public UserRepository() {
        list = new ArrayList<>();
        list.add(new User(1L, "smith@email.com", "Ron", "Smith",
                "1991-12-05", "Kyiv", "095-999-99-99"));
        list.add(new User(2L, "brown@email.com", "Kate", "Brown",
                "1996-09-17", "Warsaw", "044-444-44-44"));
    }

    @Override
    public List<User> findAll() {
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
        return Optional.of(foundUser);
    }

    @Override
    public User save(User newUser) {

        User user = new User();
        user
                .setId(newUser.getId())
                .setEmail(newUser.getEmail())
                .setFirstName(newUser.getFirstName())
                .setLastName(newUser.getLastName())
                .setBirthDate(newUser.getBirthDate())
                .setAddress(newUser.getAddress())
                .setPhoneNumber(newUser.getPhoneNumber());
        list.add(user);
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        List<User> allUsers = findAll();
        return allUsers.stream().filter(user -> Objects.equals(user.getEmail(), email)).findFirst();
    }


}
