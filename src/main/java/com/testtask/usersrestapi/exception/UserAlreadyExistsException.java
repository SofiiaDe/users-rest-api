package com.testtask.usersrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown by system in case someone tries to create user with already
 * existing email in the system.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserAlreadyExistsException extends RuntimeException{

  public UserAlreadyExistsException(String errorMessage) {
    super(errorMessage);
  }

}
