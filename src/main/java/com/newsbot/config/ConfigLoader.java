package com.newsbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

public class ConfigLoader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AppConfig loadConfig() {
        AppConfig config = null;
        try {
            File configFile = new File("config.json");
            if (configFile.exists()) {
                logger.info("Loading configuration from external file: config.json");
                config = objectMapper.readValue(configFile, AppConfig.class);
            } else {
                logger.info("Loading configuration from classpath: config.json");
                InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream("config.json");
                if (is != null) {
                    config = objectMapper.readValue(is, AppConfig.class);
                }
            }
        } catch (Exception e) {
            logger.warn("Failed to load config.json, using default configuration: {}", e.getMessage());
        }

        if (config == null) {
            config = new AppConfig();
        }

        // Hydrate from environment variables
        config.setTelegramBotToken(getEnv("TELEGRAM_BOT_TOKEN", config.getTelegramBotToken()));
        config.setTelegramChatId(getEnv("TELEGRAM_CHAT_ID", config.getTelegramChatId()));
        config.setGeminiApiKey(getEnv("GEMINI_API_KEY", config.getGeminiApiKey()));

        return config;
    }

    private static String getEnv(String name, String fallback) {
        String val = System.getenv(name);
        return (val != null && !val.trim().isEmpty()) ? val.trim() : fallback;
    }
}
