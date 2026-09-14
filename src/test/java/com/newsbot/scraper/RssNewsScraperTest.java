package com.newsbot.scraper;

import com.newsbot.config.SourceConfig;
import com.newsbot.model.NewsItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RssNewsScraperTest {

    @Test
    public void testScrapeVnExpress() {
        SourceConfig config = new SourceConfig("VnExpress Số Hóa", "https://vnexpress.net/rss/so-hoa.rss", "Technology");
        RssNewsScraper scraper = new RssNewsScraper();
        List<NewsItem> items = scraper.scrape(config, 2);
        assertNotNull(items);
        assertFalse(items.isEmpty());
        NewsItem item = items.get(0);
        assertNotNull(item.getTitle());
        assertNotNull(item.getLink());
        assertNotNull(item.getPubDate());
        assertEquals("VnExpress Số Hóa", item.getSourceName());
    }
}
