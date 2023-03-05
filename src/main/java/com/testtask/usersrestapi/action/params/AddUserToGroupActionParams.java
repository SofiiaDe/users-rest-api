package com.testtask.usersrestapi.action.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AddUserToGroupActionParams extends ActionParams {

  private Long userId;
  private Long groupId;

}
