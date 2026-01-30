package com.jedr.portfolio_backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jedr.portfolio_backend.dto.ContactRequest;
import com.jedr.portfolio_backend.service.EmailService;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*") // allow Netlify frontend
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> sendContact(
            @Valid @RequestBody ContactRequest request) {
//        System.out.println("CONTACT API HIT: " + request.getEmail());
        emailService.sendContactEmail(request);
        return ResponseEntity.ok("Thank you! Your message has been sent.");

    }
}
