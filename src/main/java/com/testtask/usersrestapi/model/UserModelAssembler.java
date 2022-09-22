package com.testtask.usersrestapi.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.testtask.usersrestapi.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {

  /**
   * Converts User objects to EntityModel<User> objects.
   *
   * @param userDto Object of User type
   * @return EntityModel<User>
   */
  @Override
  public EntityModel<UserDto> toModel(UserDto userDto) {

    return EntityModel.of(userDto, //
        linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel(),
        linkTo(methodOn(UserController.class).getAll()).withRel("users"));
  }

}