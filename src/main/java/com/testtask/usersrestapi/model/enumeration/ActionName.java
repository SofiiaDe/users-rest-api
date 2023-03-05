package com.testtask.usersrestapi.model.enumeration;

public enum ActionName {
  SCHEDULE("scheduleVisit"), GROUP("addUserToGroup");

  private String value;
  ActionName(String actionName) {
    value = actionName;
  }

  public static ActionName fromString(String string) {
    return Enum.valueOf(ActionName.class, string.trim().toUpperCase());
  }

  public static String getValue(ActionName actionName) {
    return actionName.value;
  }
}
