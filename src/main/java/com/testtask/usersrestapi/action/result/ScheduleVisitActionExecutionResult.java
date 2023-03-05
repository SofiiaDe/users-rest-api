package com.testtask.usersrestapi.action.result;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ScheduleVisitActionExecutionResult extends ActionExecutionResult {

  private String email;
  private String userFullName;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @DateTimeFormat(pattern = "HH:mm")
  private LocalTime startTime;

  @DateTimeFormat(pattern = "HH:mm")
  private LocalTime endTime;
}
