package com.testtask.usersrestapi.action.params;

public interface IActionParamsFactory {

  ActionParams createActionParams(String actionAName, String jsonString);
}
