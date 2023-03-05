package com.testtask.usersrestapi.action;

import com.testtask.usersrestapi.action.params.ActionParams;
import com.testtask.usersrestapi.action.params.ScheduleVisitActionParams;
import com.testtask.usersrestapi.action.result.ScheduleVisitActionExecutionResult;
import com.testtask.usersrestapi.service.IVisitService;
import com.testtask.usersrestapi.utils.validation.ValidationResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduleVisitAction extends ActionBase<ScheduleVisitActionExecutionResult>{

  private IVisitService visitService;

  @Override
  protected ValidationResult onValidate() {
    return null;
  }

  @Override
  protected ScheduleVisitActionExecutionResult onExecute(ActionParams actionParams) {
    return visitService.scheduleVisit((ScheduleVisitActionParams) actionParams);
  }
}

