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

        String cleanKey = apiKey.trim().replaceAll("^\"|\"$", "");
        logger.info("Using cleaned Gemini API Key length: {}", cleanKey.length());

        boolean isInternational = item.getSourceName().toLowerCase().contains("reuters")
                || item.getSourceName().toLowerCase().contains("techcrunch")
                || item.getCategory().toLowerCase().contains("international");

        String systemPrompt = "Bạn là chuyên gia phân tích tin tức hàng đầu. Hãy phân tích kỹ bài báo và trả về duy nhất 1 JSON object có cấu trúc:\n"
                + "{\n"
                + "  \"title_vn\": \"Dịch tiêu đề bài báo sang Tiếng Việt mượt mà\",\n"
                + "  \"summary_vn\": \"Bản tóm tắt cực kỳ chi tiết, đầy đủ và chuyên sâu nội dung bài viết BẰNG TIẾNG VIỆT 100% (từ 8 đến 12 câu văn mạch lạc, phân tích bối cảnh, sự kiện chính, số liệu quan trọng và kết luận). Tuyệt đối không dùng bất kỳ icon hay emoji nào.\",\n"
                + "  \"summary_en\": \"A comprehensive and detailed English summary of the article (8 to 12 sentences covering context, key events, data, and core conclusions). Strictly no emojis or icons.\",\n"
                + "  \"questions\": [\n"
                + "    \"Câu hỏi 1 (bằng Tiếng Việt): Đặt câu hỏi phân tích bám sát sự kiện chính trong bài báo\",\n"
                + "    \"Câu hỏi 2 (bằng Tiếng Việt): Đặt câu hỏi về tác động trực tiếp của thông tin này đến thị trường\",\n"
                + "    \"Câu hỏi 3 (bằng Tiếng Việt): Đặt câu hỏi về thách thức hoặc cơ hội tiếp theo\"\n"
                + "  ],\n"
                + "  \"insights\": \"Nhận định chuyên môn và dự đoán xu hướng tương lai chuyên sâu bằng Tiếng Việt\"\n"
                + "}\n"
                + "Yêu cầu tuyệt đối: Trả về đúng JSON. Tuyệt đối không dùng bất kỳ icon hay emoji nào trong toàn bộ văn bản.";

        String userPrompt = "Nguồn tin: " + item.getSourceName() + "\nTiêu đề: " + item.getTitle() + "\nNội dung: " + item.getSnippet();

        String[] modelEndpoints = {
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=",
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent?key=",
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-exp:generateContent?key=",
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
        };

        boolean success = false;
        for (String baseEndpoint : modelEndpoints) {
            try {
                String endpoint = baseEndpoint + cleanKey;
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
                    logger.info("Successfully generated Gemini AI summary via endpoint: {}", baseEndpoint);
                    success = true;
                    break;
                } else {
                    logger.warn("Endpoint {} returned status {}: {}", baseEndpoint, response.statusCode(), response.body());
                }
            } catch (Exception e) {
                logger.warn("Failed endpoint {}: {}", baseEndpoint, e.getMessage());
            }
        }

        if (!success) {
            logger.warn("All Gemini API endpoints failed or returned non-200. Applying fallback for: {}", item.getTitle());
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

                String titleVn = aiJson.path("title_vn").asText("");
                if (titleVn != null && !titleVn.trim().isEmpty()) {
                    item.setTitleVn(titleVn.trim());
                }

                String summaryVn = aiJson.path("summary_vn").asText(item.getSnippet());
                if (summaryVn == null || summaryVn.trim().isEmpty()) {
                    summaryVn = aiJson.path("summary").asText(item.getSnippet());
                }
                item.setSummary(summaryVn.trim());

                String engSummary = aiJson.path("summary_en").asText("");
                if (engSummary == null || engSummary.trim().isEmpty()) {
                    engSummary = aiJson.path("english_summary").asText("");
                }
                if (engSummary != null && !engSummary.trim().isEmpty()) {
                    item.setEnglishSummary(engSummary.trim());
                }

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
                    item.setInsights(insights.trim());
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
            q.add("Sự kiện trong bài báo '" + item.getTitle() + "' tác động trực tiếp thế nào đến ngành " + item.getCategory() + "?");
            q.add("Những rủi ro và thách thức chính mà các bên liên quan sẽ gặp phải sau thông tin này là gì?");
            q.add("Thị trường và người dùng cuối nên chuẩn bị những giải pháp ứng phó nào?");
            item.setQuestions(q);
        }
        if (item.getInsights() == null || item.getInsights().isEmpty()) {
            item.setInsights("Tin tức phản ánh xu hướng phát triển nổi bật trong ngành " + item.getCategory() + ". Cần tiếp tục quan sát diễn biến thực tế.");
        }
    }
}
