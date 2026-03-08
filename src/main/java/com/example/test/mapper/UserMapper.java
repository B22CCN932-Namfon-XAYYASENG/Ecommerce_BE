package com.example.test.mapper;

import com.example.test.dto.request.CreateUserRequest;
import com.example.test.dto.request.UpdateUserRequest;
import com.example.test.dto.response.UserResponse;
import com.example.test.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserRequest request);

    UserResponse toUserResponse(User user);

    User toUserUpdate(UpdateUserRequest request);


}
