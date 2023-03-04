package com.testtask.usersrestapi.controller;

import com.testtask.usersrestapi.model.UserDto;
import com.testtask.usersrestapi.model.UserModelAssembler;
import com.testtask.usersrestapi.model.payload.request.SearchRequest;
import com.testtask.usersrestapi.service.IUserService;
import com.testtask.usersrestapi.utils.validation.DateRangeParameters;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    private final UserModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<UserDto>> getAll() {

        List<EntityModel<UserDto>> users = userService.getAllUsers().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody UserDto newUser) {

        EntityModel<UserDto> entityModel = assembler.toModel(userService.createUser(newUser));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable Long id) {

        UserDto userDto = userService.getUserById(id);

        return assembler.toModel(userDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody UserDto newUser) {

        UserDto updatedUser = userService.updateUser(newUser);

        EntityModel<UserDto> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateUser(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {

        UserDto partiallyUpdatedUser = userService.patchUpdateUser(updates, id);

        EntityModel<UserDto> entityModel = assembler.toModel(partiallyUpdatedUser);

        return ResponseEntity
                .ok(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public CollectionModel<EntityModel<UserDto>> searchUsersByBirthDateRange(
            @Valid DateRangeParameters parameters) {

        List<EntityModel<UserDto>> users = userService.searchUsersByBirthDate(parameters.from(), parameters.to()).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @PostMapping(value = "/search")
    public Page<UserDto> search(@RequestBody SearchRequest request) {
        return userService.searchUser(request);
    }

}
