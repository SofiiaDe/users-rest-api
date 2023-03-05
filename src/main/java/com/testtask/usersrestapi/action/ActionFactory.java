package com.testtask.usersrestapi.action;

import com.testtask.usersrestapi.service.IUserService;
import com.testtask.usersrestapi.service.IVisitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ActionFactory implements IActionFactory {

  private IUserService userService;
  private IVisitService visitService;

  @Override
  public ActionBase createAction(String actionName) {

    return switch (actionName) {
      case "scheduleVisit" -> new ScheduleVisitAction(visitService);
      case "addUserToGroup" -> new AddUserToGroupAction(userService);
      default -> throw new IllegalStateException("Action '" + actionName + "' does not exist");
    };
  }

}
