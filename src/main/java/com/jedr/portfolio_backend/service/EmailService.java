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
            // VERIFIED sender (YOU – must be verified in Brevo)
            "sender", Map.of(
                "name", "Portfolio Contact",
                "email", "johnelmar88@gmail.com"
            ),

            // Email goes to YOU
            "to", List.of(
                Map.of("email", "johnelmar88@gmail.com")
            ),

            // Reply goes to recruiter/client
            "replyTo", Map.of(
                "email", request.getEmail(),
                "name", request.getName()
            ),

            "subject", "Portfolio Inquiry: " + request.getSubject(),

            "textContent",
                "You received a new portfolio inquiry.\n\n" +
                "Name: " + request.getName() + "\n" +
                "Email: " + request.getEmail() + "\n\n" +
                "Message:\n" + request.getMessage()
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
