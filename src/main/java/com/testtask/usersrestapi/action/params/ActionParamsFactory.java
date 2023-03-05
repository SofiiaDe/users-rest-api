package com.testtask.usersrestapi.action.params;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.usersrestapi.exception.ConvertFromJsonToJavaObjectException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ActionParamsFactory implements IActionParamsFactory {

  private static final String CONVERT_EXCEPTION = "Can't convert a JSON string into an ActionParams object";

  public ActionParams createActionParams(String actionName, String jsonString) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return switch (actionName) {
        case "scheduleVisit" -> objectMapper.readValue(jsonString, ScheduleVisitActionParams.class);
        case "addUserToCommunity" ->
            objectMapper.readValue(jsonString, AddUserToCommunityActionParams.class);
        default -> throw new IllegalStateException("Action '" + actionName + "' does not exist");
      };
    } catch (JsonProcessingException e) {
      log.error(CONVERT_EXCEPTION);
      throw new ConvertFromJsonToJavaObjectException(CONVERT_EXCEPTION);
    }

  }

}
