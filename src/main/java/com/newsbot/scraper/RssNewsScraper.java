package com.newsbot.scraper;

import com.newsbot.config.SourceConfig;
import com.newsbot.model.NewsItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RssNewsScraper implements NewsScraper {
    private static final Logger logger = LoggerFactory.getLogger(RssNewsScraper.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public List<NewsItem> scrape(SourceConfig sourceConfig, int maxArticles) {
        List<NewsItem> items = new ArrayList<>();
        try {
            logger.info("Scraping RSS feed: {} ({})", sourceConfig.getName(), sourceConfig.getUrl());
            URL feedUrl = new URL(sourceConfig.getUrl());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            int count = 0;
            for (SyndEntry entry : feed.getEntries()) {
                if (count >= maxArticles) {
                    break;
                }

                String title = cleanText(entry.getTitle());
                String link = entry.getLink();
                String rawDescription = entry.getDescription() != null ? entry.getDescription().getValue() : "";
                String snippet = cleanHtml(rawDescription);

                Date pubDateObj = entry.getPublishedDate() != null ? entry.getPublishedDate() : new Date();
                String formattedDate = DATE_FORMAT.format(pubDateObj);

                NewsItem item = new NewsItem(title, link, snippet, sourceConfig.getName(), sourceConfig.getCategory(), formattedDate);
                items.add(item);
                count++;
            }
            logger.info("Successfully fetched {} items from {}", items.size(), sourceConfig.getName());
        } catch (Exception e) {
            logger.error("Failed to scrape RSS feed for {}: {}", sourceConfig.getName(), e.getMessage());
        }
        return items;
    }

    private String cleanHtml(String html) {
        if (html == null || html.trim().isEmpty()) {
            return "";
        }
        String text = Jsoup.parse(html).text();
        return cleanText(text);
    }

    private String cleanText(String text) {
        if (text == null) return "";
        return text.replaceAll("\\s+", " ").trim();
    }
}
