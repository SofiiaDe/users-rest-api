package com.testtask.usersrestapi.exception;

public class ActionProcessingException extends RuntimeException {

  public ActionProcessingException(String errorMessage) {
    super(errorMessage);
  }

}
