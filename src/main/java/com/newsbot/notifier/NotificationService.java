package com.newsbot.notifier;

import com.newsbot.model.NewsItem;

import java.util.List;

public interface NotificationService {
    boolean sendNotification(List<NewsItem> items);
}
