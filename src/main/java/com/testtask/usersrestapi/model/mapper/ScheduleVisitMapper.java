package com.testtask.usersrestapi.model.mapper;

import com.testtask.usersrestapi.action.params.ScheduleVisitActionParams;
import com.testtask.usersrestapi.model.Visit;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class ScheduleVisitMapper implements EntityMapper<ScheduleVisitActionParams, Visit> {

}
