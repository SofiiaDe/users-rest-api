package com.testtask.usersrestapi.action.types;

import com.testtask.usersrestapi.action.ActionBase;
import com.testtask.usersrestapi.action.params.ActionParams;
import com.testtask.usersrestapi.action.params.ScheduleVisitActionParams;
import com.testtask.usersrestapi.action.result.ScheduleVisitActionExecutionResult;
import com.testtask.usersrestapi.action.validation.ScheduleVisitValidationResult;
import com.testtask.usersrestapi.service.IVisitService;
import com.testtask.usersrestapi.action.validation.ValidationResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduleVisitAction extends ActionBase<ScheduleVisitActionExecutionResult> {

  private IVisitService visitService;

  @Override
  protected ValidationResult onValidate(ActionParams actionParams) {
    ValidationResult validationResult = new ScheduleVisitValidationResult();
    ScheduleVisitActionParams scheduleVisitActionParams = (ScheduleVisitActionParams) actionParams;
    if (scheduleVisitActionParams.getStartTime().isAfter(scheduleVisitActionParams.getEndTime())) {
      validationResult.setSuccess(false).setUserFriendlyMessage("`start` should be more recent then `end`");
    } else {
      validationResult.setSuccess(true).setUserFriendlyMessage("Data to schedule user's visit is valid");
    }
    return validationResult;
  }

  @Override
  protected ScheduleVisitActionExecutionResult onExecute(ActionParams actionParams) {
    return visitService.scheduleVisit((ScheduleVisitActionParams) actionParams);
  }
}

