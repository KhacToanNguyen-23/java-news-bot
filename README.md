# 📰 Daily News Telegram Bot (Pet Project)

> **Dự án cá nhân (Pet Project):** Hệ thống tự động thu thập tin tức thời sự & công nghệ hàng ngày, sử dụng Google Gemini AI để tóm tắt, phân tích chuyên sâu (song ngữ Anh - Việt) và tự động gửi báo cáo điểm tin mỗi sáng qua Telegram hoàn toàn miễn phí.

---

## 📸 Thành Quả Thực Tế (Demo)

Bản tin được gửi trực tiếp về Telegram cá nhân mỗi sáng với thông tin phân tách rõ ràng, có tiêu đề, tóm tắt song ngữ, câu hỏi đào sâu và nhận định xu hướng chuyên môn:

| Bản tin sáng & Tóm tắt báo quốc tế | Nhận định chuyên môn & Link bài báo |
| :---: | :---: |
| <img src="assets/images/telegram-demo-1.png" alt="Telegram Demo 1" width="360"/> | <img src="assets/images/telegram-demo-2.png" alt="Telegram Demo 2" width="360"/> |

---

## ⚡ Công Nghệ Sử Dụng (Tech Stack)

* **Ngôn ngữ & Runtime:** Java 17 (LTS), quản lý dự án bằng **Maven**.
* **AI Engine:** **Google Gemini API** (`gemini-3.6-flash`) để đọc hiểu, tóm tắt chi tiết 8–12 câu, trích xuất nhận định và đặt câu hỏi phản biện.
* **Thu thập dữ liệu (Scraper):** 
  * **ROME Tools** (`rome`): Parse chuẩn các luồng RSS 2.0 / Atom từ VnExpress, CafeF, Reuters, TechCrunch.
  * **Jsoup**: Làm sạch mã HTML, trích xuất text thuần túy và link gốc.
* **Xử lý dữ liệu:** **FasterXML Jackson** (`jackson-databind`) đọc/ghi file cấu hình JSON và parse payload AI.
* **Kênh thông báo:** **Telegram Bot API** (gửi HTTP qua `java.net.http.HttpClient` native của Java).
* **CI/CD & Serverless Automation:** **GitHub Actions** thiết lập Cron schedule chạy tự động vào mỗi sáng (07:15 AM ICT) không cần treo máy tính hay thuê VPS.

---

## 📂 Cấu Trúc Repository

```text
newspaper/
├── .github/workflows/
│   └── daily-news.yml             # Workflow GitHub Actions hẹn giờ chạy mỗi sáng
├── assets/images/                 # Ảnh chụp màn hình kết quả thực tế trên Telegram
│   ├── telegram-demo-1.png
│   └── telegram-demo-2.png
├── src/
│   ├── main/
│   │   ├── java/com/newsbot/
│   │   │   ├── ai/                # Module AI (GeminiSummarizer, AISummarizer)
│   │   │   ├── config/            # Quản lý cấu hình nguồn tin & lịch trình
│   │   │   ├── model/             # Data Models (NewsItem, BotConfig)
│   │   │   ├── notifier/          # Module gửi tin (TelegramNotificationService, MessageFormatter)
│   │   │   ├── scraper/           # Module cào và lọc tin tức RSS
│   │   │   └── Main.java          # Entry point điều phối toàn bộ workflow
│   │   └── resources/
│   │       └── config.json        # File cấu hình danh sách nguồn RSS và số lượng bài
│   └── test/                      # Unit test kiểm thử scraper và formatter
├── pom.xml                        # Maven dependencies & Shade plugin đóng gói Fat JAR
└── README.md                      # Tài liệu hướng dẫn dự án
```

---

## ⚙️ Hướng Dẫn Cài Đặt & Sử Dụng

### 1. Chuẩn Bị Các Khóa API (Môi trường)
Dự án cần 3 biến môi trường để hoạt động:
* `TELEGRAM_BOT_TOKEN`: Token của bot Telegram (tạo miễn phí qua [@BotFather](https://t.me/BotFather)).
* `TELEGRAM_CHAT_ID`: Chat ID nhận tin nhắn (lấy qua [@userinfobot](https://t.me/userinfobot)).
* `GEMINI_API_KEY`: API Key miễn phí từ [Google AI Studio](https://aistudio.google.com/).

---

### 2. Chạy Thử Ở Máy Local

Yêu cầu máy đã cài **JDK 17+** và **Maven**.

```bash
# 1. Clone repository
git clone https://github.com/KhacToanNguyen-23/java-news-bot.git
cd java-news-bot

# 2. Thiết lập biến môi trường (Windows PowerShell)
$env:TELEGRAM_BOT_TOKEN="your_telegram_bot_token"
$env:TELEGRAM_CHAT_ID="your_telegram_chat_id"
$env:GEMINI_API_KEY="your_gemini_api_key"

# 3. Build file Fat JAR
mvn clean package -DskipTests

# 4. Khởi chạy bot
java -jar target/java-news-bot-1.0.0.jar
```

---

### 3. Thiết Lập Tự Động Hoàn Toàn Với GitHub Actions (Khuyên Dùng)

Bạn không cần mở máy tính hay thuê VPS, GitHub sẽ tự động chạy bot:

1. Fork hoặc Push mã nguồn lên Repository cá nhân trên GitHub.
2. Vào **Settings** ➔ **Secrets and variables** ➔ **Actions** ➔ Nhấn **New repository secret**.
3. Thêm 3 secret:
   * `TELEGRAM_BOT_TOKEN`
   * `TELEGRAM_CHAT_ID`
   * `GEMINI_API_KEY`
4. **Xong!** Bot sẽ tự động chạy vào lúc **07:15 AM (Giờ Việt Nam)** hàng ngày. Bạn cũng có thể vào tab **Actions** ➔ Chọn **Daily News Aggregator Bot** ➔ Bấm **Run workflow** để bot gửi tin tức ngay lập tức bất cứ lúc nào.

---

## 🎯 Tùy Biến Nguồn Báo (`config.json`)

Bạn có thể chỉnh sửa file `src/main/resources/config.json` để thêm bớt các đầu báo yêu thích:

```json
{
  "maxArticlesPerSource": 2,
  "enableAiSummary": true,
  "sources": [
    {
      "name": "Reuters World & Tech",
      "url": "https://news.google.com/rss/search?q=site:reuters.com+when:24h&hl=en-US&gl=US&ceid=US:en",
      "category": "Technology & Economics (International)"
    },
    {
      "name": "TechCrunch",
      "url": "https://techcrunch.com/feed/",
      "category": "Technology (International)"
    },
    {
      "name": "VnExpress Tin Mới Nhất",
      "url": "https://vnexpress.net/rss/tin-moi-nhat.rss",
      "category": "Technology & Economics"
    }
  ]
}
```
