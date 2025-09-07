package com.fefe.user_service.util.mapstruct;

import com.fefe.user_service.model.CreateUserRequest;
import com.fefe.user_service.model.CreateUserResponse;
import com.fefe.user_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "loyaltyCardNumber", ignore = true)
    @Mapping(target = "email", expression = "java(request.getEmail() != null ? request.getEmail().toLowerCase() : null)")
    User createUserRequestToUser(CreateUserRequest request);

    CreateUserResponse userToCreateUserResponse(User user);
}
