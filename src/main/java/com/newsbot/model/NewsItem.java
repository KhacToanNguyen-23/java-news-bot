package com.newsbot.model;

import java.util.ArrayList;
import java.util.List;

public class NewsItem {
    private String title;
    private String titleVn;          // Translated Vietnamese Title
    private String link;
    private String snippet;
    private String sourceName;
    private String category;
    private String pubDate;

    // Processed analytical fields
    private String summary;          // Detailed Vietnamese summary
    private String englishSummary;   // Detailed English summary
    private List<String> questions = new ArrayList<>();
    private String insights;

    public NewsItem() {
    }

    public NewsItem(String title, String link, String snippet, String sourceName, String category, String pubDate) {
        this.title = title;
        this.link = link;
        this.snippet = snippet;
        this.sourceName = sourceName;
        this.category = category;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleVn() {
        return titleVn;
    }

    public void setTitleVn(String titleVn) {
        this.titleVn = titleVn;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEnglishSummary() {
        return englishSummary;
    }

    public void setEnglishSummary(String englishSummary) {
        this.englishSummary = englishSummary;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public String getInsights() {
        return insights;
    }

    public void setInsights(String insights) {
        this.insights = insights;
    }
}
