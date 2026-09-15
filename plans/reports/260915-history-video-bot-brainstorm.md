# Brainstorm: Python History Video Automation Bot

**Date:** 2026-09-15

## Key Architectural Decisions
1. **Architecture Model**: Lean Python ChatOps (Zero-UI) Architecture. No n8n, no web frontend, no complex database required.
2. **Approval Checkpoints (Human-in-the-Loop)**:
   - **Checkpoint 1:** User approves/rejects AI-generated history script on Telegram.
   - **Checkpoint 2:** User reviews final rendered 9:16 `.mp4` video on Telegram and clicks `[Publish Now]`.
3. **Core Engine**:
   - **Scripting:** Google Gemini 1.5/2.0 Flash API ($0 cost).
   - **Voice TTS:** Python `edge-tts` with `vi-VN-NamMinhNeural` ($0 cost).
   - **Image Generation:** Leonardo.ai / Pollinations AI API ($0 cost).
   - **Video Renderer:** Python `MoviePy` / `FFmpeg` ($0 cost).
   - **Publishing:** YouTube Data API v3 ($0 cost).

## User's Final Direction
- Single-user personal automation tool.
- Zero hands-on editing effort: user only performs 2 approval checkpoints via Telegram Inline Keyboard buttons.
- Target niche: History stories & events formatted for YouTube Shorts / TikTok.
