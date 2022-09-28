package com.testtask.usersrestapi.utils;

import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

  @Mappings({
      @Mapping(source = "id", target = "id"),
      @Mapping(source = "email", target = "email"),
      @Mapping(source = "firstName", target = "firstName"),
      @Mapping(source = "lastName", target = "lastName"),
      @Mapping(source = "birthDate", target = "birthDate"),
      @Mapping(source = "address", target = "address"),
      @Mapping(source = "phoneNumber", target = "phoneNumber")

  })
  UserDto userToDto(User user);

  @InheritInverseConfiguration
  User dtoToUser(UserDto userDto);
}

