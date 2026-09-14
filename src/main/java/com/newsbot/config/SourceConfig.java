package com.newsbot.config;

public class SourceConfig {
    private String name;
    private String url;
    private String category;

    public SourceConfig() {
    }

    public SourceConfig(String name, String url, String category) {
        this.name = name;
        this.url = url;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
