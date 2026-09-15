package com.newsbot.notifier;

import com.newsbot.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class MessageFormatter {

    public static List<String> formatNewsReport(List<NewsItem> items) {
        List<String> messages = new ArrayList<>();
        if (items == null || items.isEmpty()) {
            messages.add("Khong co tin tuc moi trong 24h qua.");
            return messages;
        }

        StringBuilder sb = new StringBuilder();
        int maxCharLimit = 3800; // Telegram max is 4096, leave safety margin

        for (int i = 0; i < items.size(); i++) {
            NewsItem item = items.get(i);
            String formattedItem = formatSingleItem(item);

            if (sb.length() + formattedItem.length() > maxCharLimit && sb.length() > 0) {
                messages.add(sb.toString().trim());
                sb = new StringBuilder();
            }

            sb.append(formattedItem);
            if (i < items.size() - 1) {
                sb.append("\n----------------------------------------\n\n");
            }
        }

        if (sb.length() > 0) {
            messages.add(sb.toString().trim());
        }

        return messages;
    }

    public static String formatSingleItem(NewsItem item) {
        StringBuilder sb = new StringBuilder();

        sb.append("Ngày tháng: ").append(item.getPubDate() != null ? item.getPubDate() : "N/A").append("\n");
        sb.append("Tiêu đề: ").append(item.getTitle() != null ? item.getTitle() : "").append("\n");
        sb.append("Nguồn tin: ").append(item.getSourceName() != null ? item.getSourceName() : "").append("\n");
        
        String summary = item.getSummary();
        if (summary == null || summary.trim().isEmpty()) {
            summary = item.getSnippet();
        }

        if (item.getEnglishSummary() != null && !item.getEnglishSummary().trim().isEmpty()) {
            sb.append("Tóm tắt (Tiếng Việt): ").append(summary).append("\n\n");
            sb.append("Summary (English): ").append(item.getEnglishSummary().trim()).append("\n");
        } else {
            sb.append("Tóm tắt: ").append(summary).append("\n");
        }

        sb.append("\nCâu hỏi liên quan:\n");
        List<String> questions = item.getQuestions();
        if (questions != null && !questions.isEmpty()) {
            for (int i = 0; i < questions.size(); i++) {
                sb.append((i + 1)).append(". ").append(questions.get(i)).append("\n");
            }
        } else {
            sb.append("1. Bài viết này tác động thế nào đến ngành ").append(item.getCategory() != null ? item.getCategory() : "công nghệ").append("?\n");
            sb.append("2. Xu hướng này có khả năng ảnh hưởng ra sao tới thị trường Việt Nam?\n");
            sb.append("3. Những thách thức chính mà các bên liên quan phải đối mặt là gì?\n");
        }

        String insights = item.getInsights();
        if (insights == null || insights.trim().isEmpty()) {
            insights = "Tin tức phản ánh xu hướng phát triển nổi bật trong ngành " + (item.getCategory() != null ? item.getCategory() : "công nghệ") + ". Cần tiếp tục quan sát diễn biến thực tế.";
        }
        sb.append("\nNhận định & Dự đoán: ").append(insights).append("\n");
        sb.append("Link bài viết: ").append(item.getLink() != null ? item.getLink() : "").append("\n");

        return sb.toString();
    }
}
