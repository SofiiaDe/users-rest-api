package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User newUser);

    Optional<User> findUserByEmail(String email);

    void deleteById(Long id);

    List<User> findByBirthDate(LocalDate fromDate, LocalDate toDate);
}
