package com.testtask.usersrestapi.model.mapper;

import com.testtask.usersrestapi.action.params.AddUserToCommunityActionParams;
import com.testtask.usersrestapi.model.entity.UserCommunity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class AddUserToGroupMapper implements EntityMapper<AddUserToCommunityActionParams, UserCommunity> {

}
