package com.example.backend.autoMapper;

import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Named("UserToUserResponse")
    UserResponse userToUserResponse(User user);
}
