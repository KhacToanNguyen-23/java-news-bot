# java-news-bot

> A personal automation tool for news lovers who are way too lazy to scroll.

I love keeping up with Tech and Economics news, but opening 10 different tabs every morning? Nah, too lazy for that.

So I built this lightweight Java tool to do the heavy lifting for me. It automatically scrapes top headlines, gets Google Gemini AI to digest them into analytical insights (no fluff, no emojis, just pure substance), and drops a neat morning report straight into my Telegram—all for $0 using GitHub Actions while my laptop is fast asleep.

---

## What it actually does

Every morning at 07:00 AM (UTC+7), this bot:

1. Scrapes the good stuff: Grabs top Tech and Economics headlines from Reuters, VnExpress, and CafeF.
2. Asks Gemini AI to do the thinking:
   - Summarizes the core story in 2-3 sentences.
   - Generates 3 analytical questions you should be asking about the topic.
   - Provides a forward-looking prediction/insight on where the market or tech is heading.
3. Pushes to Telegram: Sends a clean, no-emoji markdown report straight to your phone.
4. Goes back to sleep: Powered by GitHub Actions cron—no 24/7 server or running PC required!

---

## The No-Emoji Analytical Format

No icons. No clutter. Just raw analytical digest:

```text
Ngày tháng: 14/09/2026 07:00
Tiêu đề: Apple unveils M5 chip focused on local AI workloads

Tóm tắt: Apple announced its next-gen M5 chip equipped with a 64-core NPU, enabling on-device execution of large language models without internet connectivity. It consumes 40% less power and doubles machine learning performance compared to the M4.

Câu hỏi liên quan:
1. How will on-device AI impact Apple's user data privacy policies?
2. How will competitors like Qualcomm or Samsung adjust their mobile chip strategies?
3. Will higher M5 manufacturing costs drive up starting prices for future MacBooks?

Nhận định & Dự đoán: This move signals a shift from Cloud AI to Edge AI on personal devices. Over the next 1-2 years, mobile apps will integrate deeper local AI features, triggering a new hardware upgrade cycle.

Link bài viết: https://reuters.com/technology/apple-m5-chip-announcement-2026
```

---

## 1-Minute Setup Guide

### 1. Fork or Clone this Repo
Push it to your personal GitHub account.

### 2. Add Secrets to GitHub
Go to Settings -> Secrets and variables -> Actions -> Click New repository secret and add:

| Secret Name | Where to get it |
| :--- | :--- |
| TELEGRAM_BOT_TOKEN | Talk to @BotFather on Telegram |
| TELEGRAM_CHAT_ID | Get your ID from @userinfobot |
| GEMINI_API_KEY | Grab a free key from Google AI Studio |

### 3. Sit back and enjoy
- Automatic: Runs every morning at 7:00 AM.
- Manual: Go to Actions tab -> Click Daily News Aggregator Bot -> Hit Run workflow anytime you want news right now!

---

## Customizing News Sources (config.json)

Want different sources? Just edit config.json directly on GitHub:

```json
{
  "scheduleCron": "0 0 7 * * *",
  "maxArticlesPerSource": 3,
  "enableAiSummary": true,
  "sources": [
    {
      "name": "Reuters Tech",
      "url": "https://news.google.com/rss/search?q=site:reuters.com+technology&hl=en-US&gl=US&ceid=US:en",
      "category": "Technology"
    },
    {
      "name": "VnExpress Số Hóa",
      "url": "https://vnexpress.net/rss/so-hoa.rss",
      "category": "Technology"
    },
    {
      "name": "CafeF Thời sự",
      "url": "https://cafef.net/rss/thoi-su.rss",
      "category": "Economics"
    }
  ]
}
```

---

## Local Testing

```bash
# Run unit tests
mvn clean test

# Build executable Fat JAR
mvn clean package

# Run locally
java -jar target/java-news-bot-1.0.0.jar
```

---

Built with Java 17, ROME RSS, Jsoup, Jackson, Gemini 1.5 Flash API, and GitHub Actions.
