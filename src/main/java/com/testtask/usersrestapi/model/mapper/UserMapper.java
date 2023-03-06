package com.testtask.usersrestapi.model.mapper;

import com.testtask.usersrestapi.model.entity.User;
import com.testtask.usersrestapi.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class UserMapper implements EntityMapper<UserDto, User> {

}

