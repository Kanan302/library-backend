package com.example.library.service.auth;

public interface EmailService {
    void sendOtpEmail(String to, String otp);
}
