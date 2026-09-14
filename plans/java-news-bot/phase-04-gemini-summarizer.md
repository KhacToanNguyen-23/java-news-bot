# Phase 4: Gemini AI Summarizer (Analytical Prompt Engineering)

**Parent Plan:** [`plans/java-news-bot/plan.md`](file:///d:/Project/FptProject/newspaper/plans/java-news-bot/plan.md)  
**Covered User Stories:** US2.1  

---

## Objectives
- Define `AISummarizer` interface.
- Implement `GeminiSummarizer` sending text payloads to Google Gemini 1.5 Flash REST API.
- Prompt Engineering: Instruct Gemini system prompt to analyze each news article and output a structured JSON response containing:
  - `summary`: Concise Vietnamese summary.
  - `questions`: Array of exactly 3 relevant analytical questions.
  - `insights`: In-depth analysis and future trend predictions.
- Enforce strict no-emoji rule in system prompt.
- Provide automatic fallback if API call fails, key is missing, or rate-limited.

## Files to Create / Modify
- [NEW] `src/main/java/com/newsbot/ai/AISummarizer.java`
- [NEW] `src/main/java/com/newsbot/ai/GeminiSummarizer.java`
- [NEW] `src/test/java/com/newsbot/ai/GeminiSummarizerTest.java`

## Verification
- Unit test with mock payload to verify JSON prompt payload construction, system prompt instructions, and response parsing.
