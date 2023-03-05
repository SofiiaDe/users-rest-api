package com.testtask.usersrestapi.action.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AddUserToGroupActionExecutionResult extends ActionExecutionResult {

  private String email;
  private String userFullName;
  private String groupTitle;
}

