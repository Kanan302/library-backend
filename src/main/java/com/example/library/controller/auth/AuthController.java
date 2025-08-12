package com.example.library.controller.auth;

import com.example.library.config.ApiResponse;
import com.example.library.dto.auth.request.*;
import com.example.library.dto.auth.response.LoginResponseDto;
import com.example.library.dto.auth.response.RegisterResponseDto;
import com.example.library.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "ugurla giris oldu", loginResponseDto));
    }

//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<RegisterResponseDto>> register(@RequestBody RegisterRequestDto registerRequestDto) {
//        RegisterResponseDto registerResponseDto = authService.register(registerRequestDto);
//        return ResponseEntity.ok(new ApiResponse<>(201, "hesab yaradildi", registerResponseDto));
//    }

    @PostMapping("/register/request-otp")
    public ResponseEntity<ApiResponse<String>> requestOtp(@RequestBody RegisterRequestDto registerRequestDto) {
        authService.requestOtp(registerRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "OTP kod emailə göndərildi", "otp sent"));
    }

    @PostMapping("/register/verify")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> verifyAndRegister(@RequestBody OtpVerificationRequestDto dto) {
        RegisterResponseDto responseDto = authService.verifyAndRegister(dto);
        return ResponseEntity.ok(new ApiResponse<>(201, "hesab yaradıldı", responseDto));
    }


    @PostMapping("/forgot-password/request-otp")
    public ResponseEntity<ApiResponse<String>> requestPasswordResetOtp(@RequestBody EmailRequestDto emailRequestDto) {
        authService.requestPasswordResetOtp(emailRequestDto.getEmail());
        return ResponseEntity.ok(new ApiResponse<>(200, "OTP kod göndərildi", null));
    }

    @PostMapping("/forgot-password/reset")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> resetPassword(@RequestBody PasswordResetRequestDto requestDto) {
        RegisterResponseDto userDto = authService.resetPassword(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Şifrə uğurla yeniləndi", userDto));
    }

}
