# Phase 2: News Scraper & RSS Parser

**Parent Plan:** [`plans/java-news-bot/plan.md`](file:///d:/Project/FptProject/newspaper/plans/java-news-bot/plan.md)  
**Covered User Stories:** US1.1  

---

## Objectives
- Create model `NewsItem` representing an article (title, link, snippet, source, publish date).
- Implement `NewsScraper` interface and `RssNewsScraper` class using ROME RSS library.
- Configure RSS feeds for Reuters Tech/Economics (via RSS/Google News RSS), VnExpress Số Hóa, CafeF Thời sự.
- Implement HTML snippet cleaning with Jsoup to sanitize raw RSS HTML descriptions.

## Files to Create / Modify
- [NEW] `src/main/java/com/newsbot/model/NewsItem.java`
- [NEW] `src/main/java/com/newsbot/scraper/NewsScraper.java`
- [NEW] `src/main/java/com/newsbot/scraper/RssNewsScraper.java`
- [NEW] `src/test/java/com/newsbot/scraper/RssNewsScraperTest.java`

## Verification
- Unit test fetching and parsing RSS feeds to confirm non-empty list of `NewsItem` objects with clean titles and valid URLs.
