package com.example.insightpulse.model;

/**
 * Represents a single news article or item.
 *
 * @param title The headline or title of the news item.
 * @param source The source or publisher of the news (e.g., "Reuters", "BBC News").
 */
public record NewsItem(String title, String source) {}