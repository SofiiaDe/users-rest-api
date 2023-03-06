package com.testtask.usersrestapi.action.types;

import com.testtask.usersrestapi.action.ActionBase;
import com.testtask.usersrestapi.action.params.ActionParams;
import com.testtask.usersrestapi.action.params.AddUserToCommunityActionParams;
import com.testtask.usersrestapi.action.result.AddUserToCommunityActionExecutionResult;
import com.testtask.usersrestapi.action.validation.AddUserToCommunityValidationResult;
import com.testtask.usersrestapi.action.validation.ValidationResult;
import com.testtask.usersrestapi.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddUserToCommunityAction extends ActionBase<AddUserToCommunityActionExecutionResult> {

    private IUserService userService;

    @Override
    protected ValidationResult onValidate(ActionParams actionParams) {
        ValidationResult validationResult = new AddUserToCommunityValidationResult();
        AddUserToCommunityActionParams addUserToCommunityActionParams = (AddUserToCommunityActionParams) actionParams;
        if (addUserToCommunityActionParams.getUserId() < 0) {
            validationResult.setSuccess(false).setUserFriendlyMessage("User ID can't be a negative value");
        } else if (addUserToCommunityActionParams.getCommunityId() < 0) {
            validationResult.setSuccess(false).setUserFriendlyMessage("Community ID can't be a negative value");
        } else {
            validationResult.setSuccess(true).setUserFriendlyMessage("Data to add user to community is valid");
        }
        return validationResult;
    }

    @Override
    protected AddUserToCommunityActionExecutionResult onExecute(ActionParams actionParams) {
        return userService.addUserToCommunity((AddUserToCommunityActionParams) actionParams);
    }
}
