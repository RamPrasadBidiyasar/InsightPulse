package com.example.insightpulse.model;

import java.util.List;

/**
 * Represents the combined, aggregated insights from all data sources for a given topic.
 * This is the primary data transfer object (DTO) returned by the API on success.
 *
 * @param topic The topic for which the insights were gathered.
 * @param news A list of news items related to the topic.
 * @param finance Financial data related to the topic.
 * @param trends A list of trending keywords related to the topic.
 * @param summary A generated summary based on the aggregated data.
 */
public record AggregatedInsight(
    String topic,
    List<NewsItem> news,
    FinanceData finance,
    List<TrendData> trends,
    String summary
) {}