# Brainstorm: Java Personal Daily News Aggregator Tool

**Date:** 2026-09-14

## Ideas Explorations & Decisions

1. **News Source Collection**:
   - Direct Web Scraping (Jsoup) for Vietnamese sites (VnExpress, CafeF, Vietnambiz).
   - Official / RSS feeds & Google News RSS for global sources (Reuters, TechCrunch, Bloomberg).
2. **Delivery Channels**:
   - Dedicated App/Web UI (Dismissed due to high UI overhead and lack of instant push notifications).
   - Zalo OA OpenAPI (Explored but flagged for OAuth 2.0 complexity and VNG policy constraints).
   - Telegram Bot API (Selected for MVP — zero UI effort, free instant push notifications, simple REST API).
   - Email / Gmail SMTP (Planned for Phase 2 via Strategy Pattern).
3. **Execution Engine (Selected: GitHub Actions)**:
   - **GitHub Actions Scheduled Workflows**: 100% Free, zero server cost, zero hardware dependency. Runs daily via cron schedule even when the user's laptop is completely powered off.
4. **Summarization & Intelligence**:
   - Raw title + URL link aggregator.
   - AI-powered auto-summarization using Gemini API (Free tier) to output 3–5 bullet points per news item.

## User's Final Direction
- **Language**: Java 17/21 (Lightweight CLI / Maven).
- **Execution Mechanism**: GitHub Actions Cron Workflow (`.github/workflows/daily-news.yml`).
- **Target Sources**: Reuters (Global Tech & Economics) + Top VN Tech/Econ news (VnExpress, CafeF).
- **Delivery Channel**: Telegram Bot (MVP) with extensible design for Email later.
- **Security**: All API keys and Bot Tokens stored in GitHub Repository Secrets.
- **Processing**: Optional AI summarization integration (Gemini API).

## Open Questions
- None (All core design decisions resolved during brainstorm).

## Risks & Mitigations
- **GitHub Cron Delay**: GitHub Actions scheduled cron can sometimes be delayed by 5-15 minutes during peak hours. Mitigation: Acceptable for daily news delivery.
- **RSS/Scraping Layout Changes**: Web scraping targets might update HTML structures. Mitigation: Rely heavily on standard RSS feeds first.
