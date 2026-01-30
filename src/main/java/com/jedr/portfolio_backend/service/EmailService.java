package com.jedr.portfolio_backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jedr.portfolio_backend.dto.ContactRequest;

@Service
public class EmailService {

    private final WebClient webClient;

    @Value("${BREVO_API_KEY}")
    private String brevoApiKey;

    public EmailService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.brevo.com")
                .build();
    }

    public void sendContactEmail(ContactRequest request) {

        Map<String, Object> body = Map.of(
            "sender", Map.of(
                "name", "Portfolio Contact",
                "email", "no-reply@portfolio.dev"   // Brevo allows this by default
            ),
            "to", List.of(
                Map.of("email", "johnelmar88@gmail.com")
            ),
            "subject", "Portfolio: " + request.getSubject(),
            "textContent",
                "Name: " + request.getName() +
                "\nEmail: " + request.getEmail() +
                "\n\nMessage:\n" + request.getMessage()
        );

        webClient.post()
            .uri("/v3/smtp/email")
            .header("api-key", brevoApiKey)
            .header("Content-Type", "application/json")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
