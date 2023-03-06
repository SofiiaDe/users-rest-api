package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.action.result.ScheduleVisitActionExecutionResult;
import com.testtask.usersrestapi.action.params.ScheduleVisitActionParams;

public interface IVisitService {

  ScheduleVisitActionExecutionResult scheduleVisit(ScheduleVisitActionParams actionParams);


}
