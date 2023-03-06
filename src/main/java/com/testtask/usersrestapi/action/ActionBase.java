package com.testtask.usersrestapi.action;

import com.testtask.usersrestapi.action.params.ActionParams;
import com.testtask.usersrestapi.action.result.ActionExecutionResult;
import com.testtask.usersrestapi.exception.ActionProcessingException;
import com.testtask.usersrestapi.action.validation.ValidationResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class ActionBase<T extends ActionExecutionResult> {

    private static final String INVALID_RESULT = "Validation failed";

    protected abstract ValidationResult onValidate(ActionParams actionParams);

    protected abstract T onExecute(ActionParams actionParams);

    public T execute(ActionParams actionParams) {
        ValidationResult validationResult = onValidate(actionParams);
        if (!validationResult.isSuccess()) {
            log.error(INVALID_RESULT);
            throw new ActionProcessingException(validationResult.getUserFriendlyMessage());
        }
        return onExecute(actionParams);
    }

}
