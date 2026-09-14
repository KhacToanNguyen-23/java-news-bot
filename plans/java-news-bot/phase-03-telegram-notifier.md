# Phase 3: Telegram Notification Engine

**Parent Plan:** [`plans/java-news-bot/plan.md`](file:///d:/Project/FptProject/newspaper/plans/java-news-bot/plan.md)  
**Covered User Stories:** US1.2, US1.4  

---

## Objectives
- Define `NotificationService` interface for sending messages.
- Implement `TelegramNotificationService` using Java 11 `HttpClient`.
- Render strict **6-part analytical report format (No Icons/Emojis)**:
  1. Ngày tháng (Date/Time)
  2. Tiêu đề (Title)
  3. Tóm tắt (Summary)
  4. Câu hỏi liên quan (3 Relevant Questions)
  5. Nhận định & Dự đoán (Insights / Predictions)
  6. Link bài viết (Article Link)
- Implement message length splitting to respect Telegram's 4096 character limit per message.

## Files to Create / Modify
- [NEW] `src/main/java/com/newsbot/notifier/NotificationService.java`
- [NEW] `src/main/java/com/newsbot/notifier/TelegramNotificationService.java`
- [NEW] `src/main/java/com/newsbot/notifier/MessageFormatter.java`
- [NEW] `src/test/java/com/newsbot/notifier/MessageFormatterTest.java`

## Verification
- Run `MessageFormatterTest` to verify exact plain-text/HTML rendering without icons and verify 4096 character message chunking logic.
