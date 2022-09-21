package com.testtask.usersrestapi.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.testtask.usersrestapi.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

  /**
   * Converts User objects to EntityModel<User> objects.
   *
   * @param user Object of User type
   * @return EntityModel<User>
   */
  @Override
  public EntityModel<User> toModel(User user) {

    return EntityModel.of(user, //
        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
        linkTo(methodOn(UserController.class).all()).withRel("employees"));
  }

}