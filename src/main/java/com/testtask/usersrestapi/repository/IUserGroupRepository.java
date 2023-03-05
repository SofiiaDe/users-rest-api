package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserGroupRepository extends JpaRepository<UserGroup, Long> {

}
