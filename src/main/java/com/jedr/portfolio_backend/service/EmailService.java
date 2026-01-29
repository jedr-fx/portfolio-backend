package com.jedr.portfolio_backend.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.jedr.portfolio_backend.dto.ContactRequest;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(ContactRequest request) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("johnelmar88@gmail.com"); // where you receive messages
        mail.setSubject("Portfolio: " + request.getSubject());

        mail.setText(
                "Name: " + request.getName() +
                        "\nEmail: " + request.getEmail() +
                        "\n\nMessage:\n" + request.getMessage()
        );


        mailSender.send(mail);
    }
}