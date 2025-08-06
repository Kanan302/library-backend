package com.example.library.mapper.auth;

import com.example.library.dto.auth.request.LoginRequestDto;
import com.example.library.dto.auth.request.RegisterRequestDto;
import com.example.library.dto.auth.response.LoginResponseDto;
import com.example.library.dto.user.UserDto;
import com.example.library.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toLoginEntity(LoginRequestDto requestDto);

    LoginResponseDto toLoginDto(User user);

    User toRegisterEntity(RegisterRequestDto requestDto);

    UserDto toRegisterDto(User user);
}
