package com.testtask.usersrestapi.controller;

import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.model.UserModelAssembler;
import com.testtask.usersrestapi.service.IUserService;
import com.testtask.usersrestapi.utils.validation.DateRangeParameters;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping("/usersApi")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    private final UserModelAssembler assembler;

    @GetMapping("/users")
    public CollectionModel<EntityModel<UserDto>> getAll() {

        List<EntityModel<UserDto>> users = userService.getAllUsers().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @PostMapping("/users")
    ResponseEntity<?> createUser(@RequestBody UserDto newUser) {

        EntityModel<UserDto> entityModel = assembler.toModel(userService.createUser(newUser));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/users/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable Long id) {

        UserDto userDto = userService.getUserById(id);

        return assembler.toModel(userDto);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUser(@RequestBody UserDto newUser) {

        UserDto updatedUser = userService.updateUser(newUser);

        EntityModel<UserDto> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PatchMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateUser(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {

        UserDto partiallyUpdatedUser = userService.patchUpdateUser(updates, id);

        EntityModel<UserDto> entityModel = assembler.toModel(partiallyUpdatedUser);

        return ResponseEntity
                .ok(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri());
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/search")
    public CollectionModel<EntityModel<UserDto>> searchUsersByBirthDateRange(
            @Valid DateRangeParameters parameters) {

        List<EntityModel<UserDto>> users = userService.searchUsersByBirthDate(parameters.from(), parameters.to()).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

}
