package com.newsbot.notifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsbot.config.AppConfig;
import com.newsbot.model.NewsItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelegramNotificationService implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(TelegramNotificationService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String botToken;
    private final String chatId;
    private final HttpClient httpClient;

    public TelegramNotificationService(AppConfig config) {
        this.botToken = config.getTelegramBotToken();
        this.chatId = config.getTelegramChatId();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public boolean sendNotification(List<NewsItem> items) {
        if (botToken == null || botToken.trim().isEmpty() || chatId == null || chatId.trim().isEmpty()) {
            logger.warn("Telegram bot token or chat ID missing. Skipping Telegram notification.");
            return false;
        }

        List<String> messages = MessageFormatter.formatNewsReport(items);
        boolean allSuccess = true;

        for (String msgText : messages) {
            boolean sent = sendMessage(msgText);
            if (!sent) {
                allSuccess = false;
            }
        }
        return allSuccess;
    }

    private boolean sendMessage(String text) {
        try {
            String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

            Map<String, Object> payload = new HashMap<>();
            payload.put("chat_id", chatId);
            payload.put("text", text);
            payload.put("disable_web_page_preview", false);

            String jsonPayload = objectMapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .timeout(Duration.ofSeconds(15))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                logger.info("Successfully sent notification to Telegram chat {}", chatId);
                return true;
            } else {
                logger.error("Failed to send Telegram message. Response HTTP {}: {}", response.statusCode(), response.body());
                return false;
            }
        } catch (Exception e) {
            logger.error("Error sending Telegram message: {}", e.getMessage(), e);
            return false;
        }
    }
}
