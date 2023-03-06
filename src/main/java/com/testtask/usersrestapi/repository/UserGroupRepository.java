package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.entity.UserCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserCommunity, Long> {

}
