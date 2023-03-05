package com.testtask.usersrestapi.service;

import com.testtask.usersrestapi.action.result.ScheduleVisitActionExecutionResult;
import com.testtask.usersrestapi.action.params.ScheduleVisitActionParams;
import com.testtask.usersrestapi.exception.UserNotFoundException;
import com.testtask.usersrestapi.model.User;
import com.testtask.usersrestapi.model.Visit;
import com.testtask.usersrestapi.model.mapper.ScheduleVisitMapper;
import com.testtask.usersrestapi.repository.IUserRepository;
import com.testtask.usersrestapi.repository.IVisitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class VisitService implements IVisitService {

  private static final String USER_NOT_FOUND = "Can't retrieve user with id = ";

  private final IVisitRepository visitRepository;
  private final IUserRepository userRepository;
  private ScheduleVisitMapper scheduleVisitMapper;

  @Override
  public ScheduleVisitActionExecutionResult scheduleVisit(
      ScheduleVisitActionParams actionParams) {

    Visit visit = visitRepository.save(scheduleVisitMapper.toEntity(actionParams));
    User user = userRepository.findById(actionParams.getUserId())
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + actionParams.getUserId()));

    ScheduleVisitActionExecutionResult result = new ScheduleVisitActionExecutionResult()
        .setEmail(user.getEmail())
        .setUserFullName(user.getFirstName() + " " + user.getLastName())
        .setDate(visit.getDate())
        .setStartTime(visit.getStartTime())
        .setEndTime(visit.getEndTime());
    result.setSuccess(true);

    return result;
  }

}
