package com.testtask.usersrestapi.action;

import com.testtask.usersrestapi.action.params.ActionParams;
import com.testtask.usersrestapi.action.params.AddUserToCommunityActionParams;
import com.testtask.usersrestapi.action.result.AddUserToCommunityActionExecutionResult;
import com.testtask.usersrestapi.action.validation.ValidationResult;
import com.testtask.usersrestapi.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddUserToCommunityAction extends ActionBase<AddUserToCommunityActionExecutionResult>{

  private IUserService userService;

  @Override
  protected ValidationResult onValidate(ActionParams actionParams) {
    return null;
  }

  @Override
  protected AddUserToCommunityActionExecutionResult onExecute(ActionParams actionParams) {
    return  userService.addUserToCommunity((AddUserToCommunityActionParams) actionParams);
  }
}
