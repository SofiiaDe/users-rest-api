package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM users u " +
            "WHERE u.birthDate >= :fromDate AND u.birthDate <= :toDate")
    List<User> findByBirthDate(@Param("fromDate") LocalDate fromDate,
                               @Param("toDate") LocalDate toDate);
}
