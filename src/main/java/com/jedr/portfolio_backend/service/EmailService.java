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
            // ✅ VERIFIED sender (YOU)
            "sender", Map.of(
                "name", "Portfolio Contact",
                "email", "johnelmar88@gmail.com"   // must be verified in Brevo
            ),

            // ✅ EMAIL GOES TO YOU
            "to", List.of(
                Map.of("email", "johnelmar88@gmail.com")
            ),

            // ✅ OPTIONAL: when you click "Reply", it replies to recruiter
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
