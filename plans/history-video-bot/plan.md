# Implementation Plan: Python History Video Automation Bot

**Slug:** `history-video-bot`  
**Date:** 2026-09-15  
**Spec:** [`plans/history-video-bot/spec.md`](file:///d:/Project/FptProject/newspaper/plans/history-video-bot/spec.md)  
**Mode:** `--hard`  
**Risk:** normal — multi-file Python automation script with REST APIs, media rendering, and Telegram Chat UI.  

---

## Phased Development Strategy

| Phase | Focus Area | Covered User Stories |
| :--- | :--- | :--- |
| **Phase 1** | Project Setup & Configuration | System environment, dependencies, `config.py` |
| **Phase 2** | AI Script Generator & Checkpoint 1 | `script_generator.py`, Gemini API prompt engineering |
| **Phase 3** | TTS Voice & Image Generation | `tts_engine.py` (edge-tts), `image_generator.py` |
| **Phase 4** | Automated 9:16 Video Rendering | `video_builder.py` (MoviePy/FFmpeg 9:16, Subtitles, BGM) |
| **Phase 5** | Telegram Chat UI & YouTube Auto-Publisher | `main.py` (Telegram Checkpoints 1 & 2), `youtube_publisher.py` |

---

## Detailed Component Breakdown

### Phase 1: Project Setup & Configuration
- Create Python virtual environment and `requirements.txt` (`python-telegram-bot`, `google-generativeai`, `edge-tts`, `moviepy`, `pillow`, `google-api-python-client`).
- Create `config.py` loading environment variables (`TELEGRAM_BOT_TOKEN`, `TELEGRAM_CHAT_ID`, `GEMINI_API_KEY`, `YOUTUBE_CLIENT_SECRET`).

### Phase 2: AI Script Generator & Checkpoint 1
- Implement `script_generator.py` calling Gemini API to produce JSON containing: `title`, `voice_text`, `image_prompts`.
- Enforce strict 60-second Vietnamese history narration guidelines.

### Phase 3: TTS Voice & Image Generation
- Implement `tts_engine.py` using `edge-tts` to generate `voice.mp3` with `vi-VN-NamMinhNeural` voice and SRT subtitles.
- Implement `image_generator.py` fetching 9:16 vertical images for each scene prompt.

### Phase 4: Automated 9:16 Video Rendering
- Implement `video_builder.py` using `MoviePy` / `FFmpeg`:
  - Fit image scenes to 9:16 aspect ratio (1080x1920).
  - Apply Ken Burns effect (subtle Zoom In / Pan).
  - Overlay background music with volume ducking under TTS narration.
  - Render burn-in Vietnamese subtitles.

### Phase 5: Telegram Chat UI & YouTube Auto-Publisher
- Implement `main.py` with `python-telegram-bot` Inline Keyboards for Checkpoint 1 (Script Approval) and Checkpoint 2 (Video Approval).
- Implement `youtube_publisher.py` utilizing YouTube Data API v3 (`videos.insert`) to upload approved Shorts.
