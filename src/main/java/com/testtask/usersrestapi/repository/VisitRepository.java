package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

}
