package com.testtask.usersrestapi.action.validation;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class ValidationResult {

  private boolean success;
  private String userFriendlyMessage;

}
