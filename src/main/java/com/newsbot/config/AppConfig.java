package com.newsbot.config;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {
    private String scheduleCron = "0 0 7 * * *";
    private int maxArticlesPerSource = 3;
    private boolean enableAiSummary = true;
    private List<SourceConfig> sources = new ArrayList<>();

    // Environment variables
    private String telegramBotToken;
    private String telegramChatId;
    private String geminiApiKey;

    public AppConfig() {
    }

    public String getScheduleCron() {
        return scheduleCron;
    }

    public void setScheduleCron(String scheduleCron) {
        this.scheduleCron = scheduleCron;
    }

    public int getMaxArticlesPerSource() {
        return maxArticlesPerSource;
    }

    public void setMaxArticlesPerSource(int maxArticlesPerSource) {
        this.maxArticlesPerSource = maxArticlesPerSource;
    }

    public boolean isEnableAiSummary() {
        return enableAiSummary;
    }

    public void setEnableAiSummary(boolean enableAiSummary) {
        this.enableAiSummary = enableAiSummary;
    }

    public List<SourceConfig> getSources() {
        return sources;
    }

    public void setSources(List<SourceConfig> sources) {
        this.sources = sources;
    }

    public String getTelegramBotToken() {
        return telegramBotToken;
    }

    public void setTelegramBotToken(String telegramBotToken) {
        this.telegramBotToken = telegramBotToken;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public String getGeminiApiKey() {
        return geminiApiKey;
    }

    public void setGeminiApiKey(String geminiApiKey) {
        this.geminiApiKey = geminiApiKey;
    }
}
