package com.example.jeliBankBackend.mapper;

import com.example.jeliBankBackend.dtos.requests.user.UserDto;
import com.example.jeliBankBackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUser(UserDto userDTO);
}
