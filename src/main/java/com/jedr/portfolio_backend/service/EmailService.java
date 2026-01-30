package com.jedr.portfolio_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.jedr.portfolio_backend.dto.ContactRequest;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(ContactRequest request) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setFrom(fromEmail); // REQUIRED for Gmail
            mail.setTo("johnelmar88@gmail.com");
            mail.setSubject("Portfolio: " + request.getSubject());

            mail.setText(
                "Name: " + request.getName() +
                "\nEmail: " + request.getEmail() +
                "\n\nMessage:\n" + request.getMessage()
            );

            mailSender.send(mail);

            System.out.println(">>> EMAIL SENT SUCCESSFULLY");

        } catch (Exception e) {
            System.out.println(">>> EMAIL FAILED");
            e.printStackTrace();
            throw new RuntimeException("Email sending failed");
        }
    }
}
