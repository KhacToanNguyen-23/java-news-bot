# Java Daily News Aggregator & Telegram Bot

Công cụ tự động hóa cá nhân bằng **Java 17** giúp cào tin tức công nghệ và kinh tế hàng ngày từ các nguồn hàng đầu (Reuters, VnExpress, CafeF), tự động phân tích và tóm tắt bài báo bằng **Google Gemini AI**, sau đó gửi bản tin về **Telegram Bot** vào khung giờ cố định.

Ứng dụng chạy hoàn toàn miễn phí, không tốn tài nguyên máy tính cá nhân thông qua **GitHub Actions Scheduled Workflows**.

---

## 📌 Định dạng bản tin (Strict Analytical Format - No Icon)

Mỗi bài báo được phân tích theo 6 phần cố định:

```text
Ngày tháng: [DD/MM/YYYY HH:mm]
Tiêu đề: [Tiêu đề bài viết]
Tóm tắt: [Tóm tắt nội dung chính bằng tiếng Việt]
Câu hỏi liên quan:
1. [Câu hỏi phân tích 1]
2. [Câu hỏi phân tích 2]
3. [Câu hỏi phân tích 3]
Nhận định & Dự đoán: [Góc nhìn chuyên sâu và dự đoán xu hướng tương lai]
Link bài viết: [URL bài báo gốc]
```

---

## 🛠️ Hướng dẫn cài đặt & Cấu hình GitHub Actions

### 1. Đưa dự án lên Repository GitHub
Đẩy toàn bộ mã nguồn dự án lên một Repository (Public hoặc Private) trên GitHub của bạn.

### 2. Cấu hình GitHub Repository Secrets
Trên GitHub, truy cập: **Settings** -> **Secrets and variables** -> **Actions** -> Bấm **New repository secret** và thêm 3 bí mật:

1. `TELEGRAM_BOT_TOKEN`: Token của Telegram Bot tạo từ `@BotFather`.
2. `TELEGRAM_CHAT_ID`: ID tài khoản Telegram cá nhân (lấy qua `@userinfobot`).
3. `GEMINI_API_KEY`: API Key lấy từ [Google AI Studio](https://aistudio.google.com/).

### 3. Tùy chỉnh lịch chạy hoặc chạy tức thì
- **Tự động:** GitHub Actions sẽ tự chạy vào **07:00 AM** (UTC+7) mỗi ngày theo cấu hình trong `.github/workflows/daily-news.yml`.
- **Chạy tức thì (On-Demand):** Mở tab **Actions** trên GitHub ➔ Chọn workflow **Daily News Aggregator Bot** ➔ Bấm nút **Run workflow**.

---

## ⚙️ Tùy chỉnh nguồn báo (`config.json`)

Bạn có thể chỉnh sửa file `config.json` trực tiếp trên web GitHub mà không cần sửa code:

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

## 💻 Chạy cục bộ (Local Development)

```bash
# Biên dịch và chạy toàn bộ unit tests
mvn clean test

# Đóng gói Fat JAR
mvn clean package

# Chạy ứng dụng (Đặt biến môi trường nếu có)
java -jar target/java-news-bot-1.0.0.jar
```
