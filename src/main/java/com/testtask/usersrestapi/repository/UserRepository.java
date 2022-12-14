package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.model.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {

    private final List<User> list;

    private static final String USER_NOT_FOUND = "Can't retrieve user with id = ";

    /**
     * Imitates database
     */
    public UserRepository() {
        list = new ArrayList<>();
        list.add(new User(1L, "smith@email.com", "Ron", "Smith",
                LocalDate.of(1991, 12, 5), "Kyiv", "095-999-99-99"));
        list.add(new User(2L, "brown@email.com", "Kate", "Brown",
                LocalDate.of(1996, 9, 17), "Warsaw", "044-444-44-44"));
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

    @Override
    public void deleteById(Long id) {
        list.remove(this.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(USER_NOT_FOUND + id);
        }));
    }

    @Override
    public List<User> findByBirthDate(LocalDate fromDate, LocalDate toDate) {

        return this.findAll().stream().filter(
                        user -> (user.getBirthDate().isAfter(fromDate) && user.getBirthDate().isBefore(toDate))
                                || user.getBirthDate().equals(fromDate) || user.getBirthDate().equals(toDate))
                .toList();
    }


}
