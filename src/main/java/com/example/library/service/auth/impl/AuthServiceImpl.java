package com.example.library.service.auth.impl;

import com.example.library.dto.auth.request.LoginRequestDto;
import com.example.library.dto.auth.request.OtpVerificationRequestDto;
import com.example.library.dto.auth.request.PasswordResetRequestDto;
import com.example.library.dto.auth.request.RegisterRequestDto;
import com.example.library.dto.auth.response.LoginResponseDto;
import com.example.library.dto.auth.response.RegisterResponseDto;
import com.example.library.entity.auth.OtpCode;
import com.example.library.entity.user.User;
import com.example.library.exception.AlreadyExistsException;
import com.example.library.exception.NotFoundException;
import com.example.library.exception.WrongPasswordException;
import com.example.library.mapper.auth.AuthMapper;
import com.example.library.repository.auth.AuthRepository;
import com.example.library.repository.auth.OtpRepository;
import com.example.library.service.auth.AuthService;
import com.example.library.service.auth.EmailService;
import com.example.library.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final OtpRepository otpRepository;


    public AuthServiceImpl(
            AuthRepository authRepository,
            AuthMapper authMapper,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            EmailService emailService,
            OtpRepository otpRepository
    ) {
        this.authRepository = authRepository;
        this.authMapper = authMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.otpRepository = otpRepository;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = authRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new WrongPasswordException("Email və ya şifrə yalnışdır."));

        boolean isPasswordMatch = passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new WrongPasswordException("Email və ya şifrə yalnışdır.");
        }

        LoginResponseDto loginResponseDto = authMapper.toLoginDto(user);

        String accessToken = jwtUtil.generateAccessToken(loginRequestDto.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(loginRequestDto.getEmail());

        loginResponseDto.setAccessToken(accessToken);
        loginResponseDto.setRefreshToken(refreshToken);

        return loginResponseDto;
    }

    @Transactional
    @Override
    public void requestOtp(RegisterRequestDto registerRequestDto) {
        if (authRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Email artıq mövcuddur.");
        }

        String otp = String.valueOf(new Random().nextInt(89999) + 10000);
        OtpCode otpCode = new OtpCode();
        otpCode.setEmail(registerRequestDto.getEmail());
        otpCode.setCode(otp);
        otpCode.setExpirationTime(LocalDateTime.now().plusMinutes(1));

        otpRepository.deleteByEmail(registerRequestDto.getEmail());
        otpRepository.save(otpCode);
        emailService.sendOtpEmail(registerRequestDto.getEmail(), otp);
    }

    @Transactional
    @Override
    public RegisterResponseDto verifyAndRegister(OtpVerificationRequestDto dto) {
        OtpCode otpCode = otpRepository.findByEmailAndCode(dto.getEmail(), dto.getOtpCode())
                .filter(o -> o.getExpirationTime().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new NotFoundException("OTP kod yalnışdır və ya vaxtı bitmişdir."));

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setReadBooks(List.of());

        user = authRepository.save(user);
        otpRepository.delete(otpCode);

        return authMapper.toRegisterDto(user);
    }

    @Override
    public void requestPasswordResetOtp(String email) {
        if (authRepository.findByEmail(email).isEmpty()) {
            throw new NotFoundException("Bu email ilə istifadəçi tapılmadı.");
        }

        String otp = String.valueOf(new Random().nextInt(89999) + 10000);
        OtpCode otpCode = new OtpCode();
        otpCode.setEmail(email);
        otpCode.setCode(otp);
        otpCode.setExpirationTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.deleteByEmail(email);
        otpRepository.save(otpCode);
        emailService.sendOtpEmail(email, otp);
    }

    @Override
    @Transactional
    public RegisterResponseDto resetPassword(PasswordResetRequestDto requestDto) {
        OtpCode otpCode = otpRepository.findByEmailAndCode(requestDto.getEmail(), requestDto.getOtpCode())
                .filter(o -> o.getExpirationTime().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new NotFoundException("OTP kod yalnışdır və ya vaxtı bitmişdir."));

        User user = authRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı."));

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        authRepository.save(user);
        otpRepository.delete(otpCode);

        return authMapper.toRegisterDto(user);
    }

}

