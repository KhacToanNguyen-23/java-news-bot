# Phase 5: Main Orchestration & GitHub Actions Workflow

**Parent Plan:** [`plans/java-news-bot/plan.md`](file:///d:/Project/FptProject/newspaper/plans/java-news-bot/plan.md)  
**Covered User Stories:** US1.3, US1.5, US2.2  

---

## Objectives
- Implement `Main.java` entry point orchestrating Config ➔ Scraper ➔ AI Summarizer ➔ Telegram Notifier.
- Configure `maven-shade-plugin` or `maven-assembly-plugin` to build executable fat JAR.
- Create `.github/workflows/daily-news.yml` for scheduled execution and manual trigger (`workflow_dispatch`).
- Document secret setup instructions for GitHub Repository Secrets (`TELEGRAM_BOT_TOKEN`, `TELEGRAM_CHAT_ID`, `GEMINI_API_KEY`).

## Files to Create / Modify
- [NEW] `src/main/java/com/newsbot/Main.java`
- [NEW] `.github/workflows/daily-news.yml`
- [NEW] `README.md`

## Verification
- Local build `mvn clean package` to verify runnable JAR execution.
- Validate `.github/workflows/daily-news.yml` syntax.
