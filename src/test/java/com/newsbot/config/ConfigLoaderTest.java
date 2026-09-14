package com.newsbot.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigLoaderTest {

    @Test
    public void testLoadConfigFromResource() {
        AppConfig config = ConfigLoader.loadConfig();
        assertNotNull(config);
        assertNotNull(config.getSources());
        assertFalse(config.getSources().isEmpty());
        assertTrue(config.getMaxArticlesPerSource() > 0);
    }
}
