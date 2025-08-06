package com.example.library.service.auth;

import com.example.library.dto.auth.request.LoginRequestDto;
import com.example.library.dto.auth.request.OtpVerificationRequestDto;
import com.example.library.dto.auth.request.PasswordResetRequestDto;
import com.example.library.dto.auth.request.RegisterRequestDto;
import com.example.library.dto.auth.response.LoginResponseDto;
import com.example.library.dto.user.UserDto;


public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    void requestOtp(RegisterRequestDto registerRequestDto);
    UserDto verifyAndRegister(OtpVerificationRequestDto dto);

    void requestPasswordResetOtp(String email);
    UserDto resetPassword(PasswordResetRequestDto requestDto);

//    RegisterResponseDto register(RegisterRequestDto registerRequestDto);
}
