# Functional Specification: Java Daily News Aggregator & Telegram Bot

**Slug:** `java-news-bot`  
**Date:** 2026-09-14  
**Status:** DRAFT / FINALIZED FORMAT  

---

## 1. Overview & Objectives

Develop a personal automation tool in **Java** that fetches tech and economics news daily from global (e.g., Reuters, TechCrunch) and domestic (e.g., VnExpress, CafeF) sources, summarizes them using AI (Gemini API) with custom analytical prompts, and sends notifications to the user via **Telegram Bot**.

The application is executed serverless & free via **GitHub Actions Scheduled Workflows**:
- No paid servers or 24/7 background process required.
- Runs automatically on GitHub's cloud runners based on a cron schedule (e.g., 07:00 AM UTC+7).
- Sends news to user's Telegram even when the user's personal computer is completely turned off.

---

## 2. Article Summary Format Requirement (Strict No-Icon Analytical Format)

Each summarized article in the Telegram message **MUST NOT use any icons/emojis**, and must adhere strictly to the following 6-part analytical format:

```text
Ngày tháng: [DD/MM/YYYY HH:mm]
Tiêu đề: [Tiêu đề bài báo]
Tóm tắt: [Nội dung tóm tắt chính của bài viết]
Câu hỏi liên quan:
1. [Câu hỏi phân tích 1]
2. [Câu hỏi phân tích 2]
3. [Câu hỏi phân tích 3]
Nhận định & Dự đoán: [Nhận định, góc nhìn chuyên sâu và dự đoán xu hướng liên quan]
Link bài viết: [URL bài báo gốc]
```

---

## 3. User Stories & Priorities

### P1 (Core MVP)
- **US1.1:** As a user, I want the Java CLI tool to collect top tech and economics news from RSS/Web sources (Reuters, VnExpress, CafeF) on execution.
- **US1.2:** As a user, I want to receive clean, formatted news reports matching the strict 6-part analytical format (Date, Title, Summary, 3 Questions, Insights/Predictions, Link) via Telegram Bot without emojis.
- **US1.3:** As a user, I want the tool to be executed automatically every day via a GitHub Actions Scheduled Workflow (`.github/workflows/daily-news.yml`) without keeping my laptop turned on.
- **US1.4:** As a user, I want sensitive tokens (Telegram Bot Token, Chat ID, API Keys) securely stored in GitHub Repository Secrets.
- **US1.5:** As a user, I want an extensible notification architecture (`NotificationService` interface) so Email support can be added later without touching core scraping logic.

### P2 (Enhancements)
- **US2.1:** As a user, I want the Gemini API prompt engineered to produce the 3 relevant analytical questions and prediction/insight for each article.
- **US2.2:** As a user, I want to manually trigger the GitHub Action workflow anytime from the GitHub Mobile app or web interface ("Workflow Dispatch") to receive news on demand.

### P3 (Future Scope)
- **US3.1:** Add Email notification delivery (`EmailNotificationService` via Gmail SMTP / GitHub Action secret).
- **US3.2:** Store previously sent news URLs in a lightweight artifact or cache to prevent duplicate news delivery across runs.

---

## 4. Architecture & Key Components

1. **Execution Engine (GitHub Actions)**:
   - Workflow file `.github/workflows/daily-news.yml`.
   - Uses `schedule` trigger with cron expression (`0 0 * * *` UTC for 07:00 AM ICT).
   - Also supports `workflow_dispatch` for manual on-demand execution.
2. **Secrets & Environment**:
   - `TELEGRAM_BOT_TOKEN`, `TELEGRAM_CHAT_ID`, `GEMINI_API_KEY` injected securely via GitHub Secrets.
3. **Crawler Module**:
   - RSS Reader (`Rome` library or Java 11 HttpClient + XML parser) for Reuters, TechCrunch, VnExpress.
   - Jsoup HTML parser fallback for Vietnamese news portals.
4. **AI Analytical Summarizer Module (Gemini API)**:
   - Java Client constructing custom prompt for Gemini 1.5 Flash to output: Summary, 3 Related Questions, and Prediction/Insight.
5. **Notifier Module**:
   - `NotificationService` interface.
   - `TelegramNotificationService` implementation rendering strict plain-text/HTML formatted output without icons.

---

## 5. Measurable Success Criteria

- **Format Compliance:** 100% of delivered articles follow the exact 6-part schema without any emojis/icons.
- **Execution Speed:** Complete GitHub Action workflow execution within `< 30 seconds`.
- **Reliability:** 100% successful delivery of Telegram notification on scheduled cron trigger.
- **Extensibility:** Interface abstraction allows implementing `EmailNotificationService` with `< 50 lines of new code`.

---

## 6. Next Steps

Proceed to `$bb-plan plans/java-news-bot/spec.md` to design implementation details, package structure, dependencies, and phase breakdown.
