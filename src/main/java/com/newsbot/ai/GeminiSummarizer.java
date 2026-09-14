package com.newsbot.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsbot.model.NewsItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeminiSummarizer implements AISummarizer {
    private static final Logger logger = LoggerFactory.getLogger(GeminiSummarizer.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient;

    public GeminiSummarizer() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public void summarize(NewsItem item, String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            logger.info("Gemini API key missing. Using fallback summary for title: {}", item.getTitle());
            applyFallback(item);
            return;
        }

        try {
            // Using official Gemini 1.5 Flash endpoint
            String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

            String systemPrompt = "Bạn là chuyên gia phân tích tin tức. Hãy phân tích bài báo và trả về duy nhất 1 JSON object có cấu trúc:\n"
                    + "{\n"
                    + "  \"summary\": \"Tóm tắt nội dung chính bằng tiếng Việt (2-3 câu)\",\n"
                    + "  \"questions\": [\"Câu hỏi 1\", \"Câu hỏi 2\", \"Câu hỏi 3\"],\n"
                    + "  \"insights\": \"Nhận định & dự đoán xu hướng tương lai\"\n"
                    + "}\n"
                    + "Yêu cầu tuyệt đối: Không dùng bất kỳ icon hay emoji nào trong câu chữ.";

            String userPrompt = "Tiêu đề: " + item.getTitle() + "\nNội dung: " + item.getSnippet();

            Map<String, Object> requestBody = buildGeminiPayload(systemPrompt, userPrompt);
            String jsonPayload = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .timeout(Duration.ofSeconds(20))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                parseAndApplyGeminiResponse(item, response.body());
            } else {
                logger.warn("Gemini API returned status {}: {}. Using fallback.", response.statusCode(), response.body());
                applyFallback(item);
            }
        } catch (Exception e) {
            logger.error("Failed to generate AI summary for article '{}': {}", item.getTitle(), e.getMessage());
            applyFallback(item);
        }
    }

    private Map<String, Object> buildGeminiPayload(String systemPrompt, String userPrompt) {
        Map<String, Object> payload = new HashMap<>();

        Map<String, Object> systemInstruction = new HashMap<>();
        Map<String, String> sysPart = new HashMap<>();
        sysPart.put("text", systemPrompt);
        systemInstruction.put("parts", List.of(sysPart));

        Map<String, Object> contents = new HashMap<>();
        Map<String, String> userPart = new HashMap<>();
        userPart.put("text", userPrompt);
        contents.put("parts", List.of(userPart));

        payload.put("system_instruction", systemInstruction);
        payload.put("contents", List.of(contents));

        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("response_mime_type", "application/json");
        generationConfig.put("temperature", 0.3);
        payload.put("generationConfig", generationConfig);

        return payload;
    }

    private void parseAndApplyGeminiResponse(NewsItem item, String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                String rawText = candidates.get(0).path("content").path("parts").get(0).path("text").asText();
                JsonNode aiJson = objectMapper.readTree(rawText);

                String summary = aiJson.path("summary").asText(item.getSnippet());
                item.setSummary(summary);

                List<String> questions = new ArrayList<>();
                JsonNode qArray = aiJson.path("questions");
                if (qArray.isArray()) {
                    for (JsonNode qNode : qArray) {
                        questions.add(qNode.asText());
                    }
                }
                if (!questions.isEmpty()) {
                    item.setQuestions(questions);
                }

                String insights = aiJson.path("insights").asText();
                if (insights != null && !insights.trim().isEmpty()) {
                    item.setInsights(insights);
                }
            } else {
                applyFallback(item);
            }
        } catch (Exception e) {
            logger.warn("Failed to parse Gemini JSON output, applying fallback: {}", e.getMessage());
            applyFallback(item);
        }
    }

    private void applyFallback(NewsItem item) {
        if (item.getSummary() == null || item.getSummary().isEmpty()) {
            item.setSummary(item.getSnippet());
        }
        if (item.getQuestions() == null || item.getQuestions().isEmpty()) {
            List<String> q = new ArrayList<>();
            q.add("Bài viết này tác động thế nào đến góc nhìn ngành " + item.getCategory() + "?");
            q.add("Cần theo dõi thêm những thông số hoặc sự kiện gì tiếp theo?");
            q.add("Những giải pháp thay thế khả thi cho xu hướng này là gì?");
            item.setQuestions(q);
        }
        if (item.getInsights() == null || item.getInsights().isEmpty()) {
            item.setInsights("Tin tức phản ánh xu hướng phát triển nổi bật trong ngành " + item.getCategory() + ". Cần tiếp tục quan sát diễn biến thực tế.");
        }
    }
}
