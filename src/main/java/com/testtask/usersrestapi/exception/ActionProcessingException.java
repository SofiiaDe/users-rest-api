package com.testtask.usersrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ActionProcessingException extends RuntimeException {

  public ActionProcessingException(String errorMessage) {
    super(errorMessage);
  }

}
