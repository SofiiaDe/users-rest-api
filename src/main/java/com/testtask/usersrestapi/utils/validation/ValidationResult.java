package com.testtask.usersrestapi.utils.validation;

import lombok.Data;

@Data
public abstract class ValidationResult {

  private boolean success;
  private String userFriendlyMessage;

}
