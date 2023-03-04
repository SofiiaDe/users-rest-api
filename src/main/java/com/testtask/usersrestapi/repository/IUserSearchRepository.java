package com.testtask.usersrestapi.repository;

import com.testtask.usersrestapi.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Repository
@Component
//@EnableJpaRepositories
public interface IUserSearchRepository extends JpaSpecificationExecutor<User> {

}
