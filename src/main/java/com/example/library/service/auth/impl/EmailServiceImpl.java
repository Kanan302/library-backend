package com.example.library.service.auth.impl;

import com.example.library.entity.books.Books;
import com.example.library.entity.user.User;
import com.example.library.repository.books.BooksRepository;
import com.example.library.service.auth.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final BooksRepository booksRepository;
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender, BooksRepository booksRepository) {
        this.mailSender = mailSender;
        this.booksRepository = booksRepository;
    }

    @Override
    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("OTP Təsdiq Kodu");
        message.setText("Sizin OTP kodunuz: " + otp);
        mailSender.send(message);
    }

    @Override
    public void sendReminder(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendReadDateReminders() {
        LocalDate today = LocalDate.now();
        List<Books> booksToRemind = booksRepository.findByReadDate(today);

        for (Books book : booksToRemind) {
            for (User user : book.getReaders()) {
                sendReminder(
                        user.getEmail(),
                        "Oxuma Xatırlatması",
                        "Salam, bu gün oxumağı planladığınız kitab: " + book.getTitle()
                );
            }
        }
    }
}
