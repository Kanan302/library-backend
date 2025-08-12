package com.example.library.mapper.auth;

import com.example.library.dto.auth.request.LoginRequestDto;
import com.example.library.dto.auth.request.RegisterRequestDto;
import com.example.library.dto.auth.response.LoginResponseDto;
import com.example.library.dto.auth.response.RegisterResponseDto;
import com.example.library.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toLoginEntity(LoginRequestDto requestDto);

    LoginResponseDto toLoginDto(User user);

    User toRegisterEntity(RegisterRequestDto requestDto);

    RegisterResponseDto toRegisterDto(User user);
}
