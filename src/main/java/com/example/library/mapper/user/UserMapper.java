package com.example.library.mapper.user;

import com.example.library.dto.user.UserDto;
import com.example.library.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
