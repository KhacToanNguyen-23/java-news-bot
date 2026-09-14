package com.newsbot.notifier;

import com.newsbot.model.NewsItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageFormatterTest {

    @Test
    public void testFormatSingleItemNoIcon() {
        NewsItem item = new NewsItem(
                "Apple Ra Mắt Chip M5",
                "https://reuters.com/tech",
                "Apple cong bo chip M5 voi 64 nhan NPU.",
                "Reuters",
                "Technology",
                "14/09/2026 07:00"
        );
        item.setSummary("Chip M5 toi uu cho AI on-device chay truc tiep khong can cloud.");
        List<String> q = new ArrayList<>();
        q.add("Tac dong den bao mat du lieu?");
        q.add("Doi thu Qualcom tra loi ra sao?");
        q.add("Gia ban thiet bi co tang?");
        item.setQuestions(q);
        item.setInsights("Xu huong On-device AI se bung no trong 1-2 nam toi.");

        String formatted = MessageFormatter.formatSingleItem(item);

        assertTrue(formatted.contains("Ngày tháng: 14/09/2026 07:00"));
        assertTrue(formatted.contains("Tiêu đề: Apple Ra Mắt Chip M5"));
        assertTrue(formatted.contains("Tóm tắt: Chip M5 toi uu cho AI on-device"));
        assertTrue(formatted.contains("Câu hỏi liên quan:"));
        assertTrue(formatted.contains("1. Tac dong den bao mat du lieu?"));
        assertTrue(formatted.contains("Nhận định & Dự đoán: Xu huong On-device AI"));
        assertTrue(formatted.contains("Link bài viết: https://reuters.com/tech"));
        // Ensure no emojis/icons in output
        assertFalse(formatted.contains("📰"));
        assertFalse(formatted.contains("🔹"));
        assertFalse(formatted.contains("🔗"));
    }
}
