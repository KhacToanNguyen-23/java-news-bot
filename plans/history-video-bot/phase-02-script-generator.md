# Phase 2: AI Script Generator & Checkpoint 1

**Parent Plan:** [`plans/history-video-bot/plan.md`](file:///d:/Project/FptProject/newspaper/plans/history-video-bot/plan.md)  
**Covered User Stories:** US1.1, US1.2  

---

## Objectives
- Implement `script_generator.py` calling Gemini API.
- Prompt Engineering to generate structured JSON containing: `title`, `voice_text`, `image_prompts`.
- Enforce strict 60-second Vietnamese history narration format without emojis.

## Files to Create
- [NEW] `modules/script_generator.py`
- [NEW] `tests/test_script_generator.py`
