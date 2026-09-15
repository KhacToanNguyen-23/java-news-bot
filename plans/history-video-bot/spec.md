# Functional Specification: Python History Video Automation Bot

**Slug:** `history-video-bot`  
**Date:** 2026-09-15  
**Status:** FINALIZED SPECIFICATION  

---

## 1. Overview & Objectives

Develop a personal Python automation bot (`history-video-bot`) that automates the generation, editing, and publishing of 60-second History YouTube Shorts / TikTok videos.

The system features a **Human-in-the-Loop Zero-UI Architecture** via **Telegram Bot**:
- **Checkpoint 1 (Script Approval):** AI generates 60s history script ➔ User approves/rejects via Telegram Inline Keyboard.
- **Auto Production:** Edge-TTS generates voice.mp3 ➔ Image AI generates scene visuals ➔ MoviePy/FFmpeg renders final 9:16 video `.mp4` with auto-subtitles and background music.
- **Checkpoint 2 (Video Approval):** System sends final `.mp4` preview to Telegram ➔ User approves publishing.
- **Auto Publish:** System calls YouTube Data API v3 to upload the video with title, description, and hashtags.

---

## 2. User Stories & Priorities

### P1 (Core MVP)
- **US1.1:** As a user, I want the bot to generate 60-second engaging Vietnamese history scripts via Google Gemini API.
- **US1.2:** As a user, I want Checkpoint 1 on Telegram where I can read the generated script and click `[Approve Script]` or `[Reject]`.
- **US1.3:** As a user, I want approved scripts to automatically generate high-quality voice audio (`edge-tts`) and scene visuals.
- **US1.4:** As a user, I want the system to render a complete 9:16 vertical video `.mp4` with Zoom/Pan effects, background music, and auto-subtitles.
- **US1.5:** As a user, I want Checkpoint 2 on Telegram where I receive the rendered `.mp4` video preview and click `[Publish Now]`.
- **US1.6:** As a user, I want approved videos automatically published to my YouTube Shorts channel via YouTube Data API v3.

---

## 3. Architecture & Key Components

```
history-video-bot/
├── config.py                 # API keys & environment settings
├── main.py                   # Telegram Bot Listener & State Manager
├── modules/
│   ├── script_generator.py   # Module 1: Gemini AI Script & Prompts Generator
│   ├── tts_engine.py         # Module 2: Edge-TTS Voice & Subtitle Generator
│   ├── image_generator.py    # Module 3: Image AI Scene Generator
│   ├── video_builder.py      # Module 4: MoviePy / FFmpeg 9:16 Video Renderer
│   └── youtube_publisher.py  # Module 5: YouTube Data API v3 Uploader
└── storage/                  # Temporary media assets (voice.mp3, scenes, final.mp4)
```

---

## 4. Measurable Success Criteria

- **Zero UI Overhead:** 100% of user interaction performed via Telegram Inline Keyboard buttons.
- **Production Cost:** $0 operating cost using free tier APIs and open-source rendering libraries.
- **Rendering Time:** Complete 60s video rendering in `< 60 seconds`.
- **Zero Manual Editing:** Auto-generates voice, images, subtitles, music, and transitions without manual CapCut/Premiere intervention.

---

## 5. Next Steps

Proceed to `$bb-plan plans/history-video-bot/spec.md` for phase breakdown and implementation plan.
