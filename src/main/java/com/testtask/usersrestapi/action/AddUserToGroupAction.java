package com.testtask.usersrestapi.action;

import com.testtask.usersrestapi.action.params.ActionParams;
import com.testtask.usersrestapi.action.params.AddUserToGroupActionParams;
import com.testtask.usersrestapi.action.result.AddUserToGroupActionExecutionResult;
import com.testtask.usersrestapi.action.validation.ValidationResult;
import com.testtask.usersrestapi.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddUserToGroupAction extends ActionBase<AddUserToGroupActionExecutionResult>{

  private IUserService userService;

  @Override
  protected ValidationResult onValidate(ActionParams actionParams) {
    return null;
  }

  @Override
  protected AddUserToGroupActionExecutionResult onExecute(ActionParams actionParams) {
    return  userService.addUserToGroup((AddUserToGroupActionParams) actionParams);
  }
}
