package com.testtask.usersrestapi.action;

import com.testtask.usersrestapi.action.params.ActionParams;
import com.testtask.usersrestapi.action.result.ActionExecutionResult;
import com.testtask.usersrestapi.exception.ActionProcessingException;
import com.testtask.usersrestapi.action.validation.ValidationResult;

public abstract class ActionBase<T extends ActionExecutionResult> {

  protected abstract ValidationResult onValidate(ActionParams actionParams);

  protected abstract T onExecute(ActionParams actionParams);

  public T execute(ActionParams actionParams) {
    ValidationResult validationResult = onValidate(actionParams);
    if (!validationResult.isSuccess()) {
      throw new ActionProcessingException(validationResult.getUserFriendlyMessage());
    }
    return onExecute(actionParams);
  }

}
