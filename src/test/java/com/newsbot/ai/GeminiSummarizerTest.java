package com.newsbot.ai;

import com.newsbot.model.NewsItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeminiSummarizerTest {

    @Test
    public void testFallbackWhenApiKeyNull() {
        NewsItem item = new NewsItem(
                "Test Article Title",
                "https://example.com/test",
                "This is a test snippet of an article.",
                "Test Source",
                "Technology",
                "14/09/2026 07:00"
        );

        GeminiSummarizer summarizer = new GeminiSummarizer();
        summarizer.summarize(item, null);

        assertNotNull(item.getSummary());
        assertEquals("This is a test snippet of an article.", item.getSummary());
        assertNotNull(item.getQuestions());
        assertEquals(3, item.getQuestions().size());
        assertNotNull(item.getInsights());
    }
}
