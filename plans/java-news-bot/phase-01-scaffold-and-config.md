# Phase 1: Project Setup & Configuration

**Parent Plan:** [`plans/java-news-bot/plan.md`](file:///d:/Project/FptProject/newspaper/plans/java-news-bot/plan.md)  
**Covered User Stories:** US1.3  

---

## Objectives
- Initialize standard Java Maven project structure.
- Configure `pom.xml` with dependencies: ROME, Jsoup, Jackson Databind, SLF4J/Logback, JUnit 5.
- Implement `AppConfig` and `ConfigLoader` to read `config.json` and environment variables (`TELEGRAM_BOT_TOKEN`, `TELEGRAM_CHAT_ID`, `GEMINI_API_KEY`).

## Files to Create / Modify
- [NEW] `pom.xml`
- [NEW] `src/main/resources/config.json`
- [NEW] `src/main/java/com/newsbot/config/AppConfig.java`
- [NEW] `src/main/java/com/newsbot/config/ConfigLoader.java`
- [NEW] `src/test/java/com/newsbot/config/ConfigLoaderTest.java`

## Verification
- Run `mvn test-compile` and unit test `ConfigLoaderTest` to ensure `config.json` and environment variables are properly mapped.
