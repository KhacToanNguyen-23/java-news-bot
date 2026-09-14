package com.newsbot.ai;

import com.newsbot.model.NewsItem;

public interface AISummarizer {
    void summarize(NewsItem item, String apiKey);
}
