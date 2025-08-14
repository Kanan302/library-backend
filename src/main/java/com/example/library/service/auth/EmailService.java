package com.example.library.service.auth;

public interface EmailService {
    void sendOtpEmail(String to, String otp);

    void sendReminder(String to, String subject, String text);

    void sendReadDateReminders(); // reminder üçün
}
