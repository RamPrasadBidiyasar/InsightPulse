package com.example.insightpulse.service;

import com.example.insightpulse.model.AggregatedInsight;
import com.example.insightpulse.model.FinanceData;
import com.example.insightpulse.model.NewsItem;
import com.example.insightpulse.model.TrendData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for combining data from multiple sources into a single,
 * unified {@link AggregatedInsight} object. It may also apply business logic
 * to generate a summary or rank the combined data.
 */
@Service
public class AggregationService {

    /**
     * Combines news, finance, and trends data into a single aggregated response.
     *
     * @param topic The topic for which the data was fetched.
     * @param news A list of news items.
     * @param finance Financial data.
     * @param trends A list of trends data.
     * @return An {@link AggregatedInsight} object containing the combined data and a summary.
     */
    public AggregatedInsight combine(String topic, List<NewsItem> news, FinanceData finance, List<TrendData> trends) {
        // Simple summary logic for demonstration purposes.
        String summary = String.format(
            "Aggregated insights for '%s'. Found %d news articles, financial data for symbol %s, and %d trends.",
            topic,
            news.size(),
            finance.stockSymbol(),
            trends.size()
        );

        return new AggregatedInsight(topic, news, finance, trends, summary);
    }
}