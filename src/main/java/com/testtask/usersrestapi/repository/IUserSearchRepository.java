package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserSearchRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
