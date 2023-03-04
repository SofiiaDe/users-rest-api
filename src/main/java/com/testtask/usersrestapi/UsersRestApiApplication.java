package com.testtask.usersrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("com.testtask.usersrestapi.repository")
//@EnableJpaRepositories
//@ComponentScan(basePackages = "com.testtask.usersrestapi")
public class UsersRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersRestApiApplication.class, args);
	}

}
