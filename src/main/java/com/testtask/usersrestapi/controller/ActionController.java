package com.testtask.usersrestapi.controller;

import com.testtask.usersrestapi.action.ActionBase;
import com.testtask.usersrestapi.action.IActionFactory;
import com.testtask.usersrestapi.action.params.ActionParams;
import com.testtask.usersrestapi.action.params.IActionParamsFactory;
import com.testtask.usersrestapi.action.result.ActionExecutionResult;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actions")
@AllArgsConstructor
public class ActionController {

  private IActionFactory actionFactory;
  private IActionParamsFactory actionParamsFactory;

  @PostMapping("/{actionName}")
  ResponseEntity<?> action(@PathVariable String actionName, @RequestBody String jsonParams) {

    ActionBase action = actionFactory.createAction(actionName);
    ActionParams actionParams = actionParamsFactory.createActionParams(actionName, jsonParams);

    ActionExecutionResult result = action.execute(actionParams);

    return ResponseEntity.ok(result);
  }

}
