package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    List<User> findByBirthDate(LocalDate fromDate, LocalDate toDate);
}
