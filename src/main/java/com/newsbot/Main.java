package com.newsbot;

import com.newsbot.ai.AISummarizer;
import com.newsbot.ai.GeminiSummarizer;
import com.newsbot.config.AppConfig;
import com.newsbot.config.ConfigLoader;
import com.newsbot.config.SourceConfig;
import com.newsbot.model.NewsItem;
import com.newsbot.notifier.NotificationService;
import com.newsbot.notifier.TelegramNotificationService;
import com.newsbot.scraper.NewsScraper;
import com.newsbot.scraper.RssNewsScraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("===============================================");
        logger.info(" Starting Java Daily News Aggregator & Bot");
        logger.info("===============================================");

        AppConfig config = ConfigLoader.loadConfig();
        logger.info("Config loaded: maxArticlesPerSource={}, enableAiSummary={}",
                config.getMaxArticlesPerSource(), config.isEnableAiSummary());

        NewsScraper scraper = new RssNewsScraper();
        AISummarizer summarizer = new GeminiSummarizer();
        NotificationService notifier = new TelegramNotificationService(config);

        List<NewsItem> allNews = new ArrayList<>();

        for (SourceConfig source : config.getSources()) {
            List<NewsItem> items = scraper.scrape(source, config.getMaxArticlesPerSource());
            allNews.addAll(items);
        }

        logger.info("Total news articles fetched: {}", allNews.size());

        if (allNews.isEmpty()) {
            logger.warn("No articles found to process.");
            return;
        }

        // Process AI Summaries
        if (config.isEnableAiSummary()) {
            logger.info("Processing AI analytical summaries via Gemini...");
            for (NewsItem item : allNews) {
                summarizer.summarize(item, config.getGeminiApiKey());
            }
        }

        // Send Notification
        logger.info("Sending notifications via Telegram...");
        boolean success = notifier.sendNotification(allNews);

        if (success) {
            logger.info("News notification sent successfully!");
        } else {
            logger.warn("Failed to send some or all notifications.");
        }

        logger.info("===============================================");
        logger.info(" Java Daily News Aggregator Completed");
        logger.info("===============================================");
    }
}
