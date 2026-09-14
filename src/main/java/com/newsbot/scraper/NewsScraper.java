package com.newsbot.scraper;

import com.newsbot.config.SourceConfig;
import com.newsbot.model.NewsItem;

import java.util.List;

public interface NewsScraper {
    List<NewsItem> scrape(SourceConfig sourceConfig, int maxArticles);
}
