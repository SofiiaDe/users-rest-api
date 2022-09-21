package com.testtask.usersrestapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.UserModelAssembler;
import com.testtask.usersrestapi.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usersApi")
@AllArgsConstructor
public class UserController {

  private final UserRepository repository;

  private final UserModelAssembler assembler;
  
  @GetMapping("/users")
  public CollectionModel<EntityModel<User>> all() {

    List<EntityModel<User>> users = repository.findAllUsers().stream() //
        .map(assembler::toModel) //
        .toList();

    return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
  }

  @PostMapping("/users")
  ResponseEntity<?> createUser(@RequestBody User newUser) {

    EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  @GetMapping("/users/{id}")
  public EntityModel<User> getUserById(@PathVariable Long id) {

    User user = repository.findById(id) //
        .orElseThrow(() -> new UserNotFoundException(id));

    return assembler.toModel(user);
  }





}
