package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVisitRepository extends JpaRepository<Visit, Long> {

}
