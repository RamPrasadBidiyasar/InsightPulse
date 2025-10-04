package com.example.insightpulse.service;

import com.example.insightpulse.model.NewsItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

/**
 * Service responsible for fetching news data from a simulated external news API.
 * In a real application, this service would make an HTTP call to a news provider.
 */
@Service
public class NewsService {

    private static final Logger log = LoggerFactory.getLogger(NewsService.class);

    /**
     * Fetches news articles for a given topic.
     * This is a mock implementation that returns a dummy list of news items.
     *
     * @param topic The topic to fetch news for.
     * @return A list of {@link NewsItem} objects.
     * @throws InterruptedException if the thread is interrupted while sleeping.
     */
    public List<NewsItem> fetchNews(String topic) throws InterruptedException {
        log.info("Fetching news for topic: {}", topic);
        // Simulate network latency
        Thread.sleep(1000);
        // In a real implementation, this would use WebClient to call an external API.
        // For this example, we return mock data.
        if ("error".equalsIgnoreCase(topic)) {
            throw new RuntimeException("News API failed");
        }
        return List.of(
            new NewsItem("Market Hits Record Highs", "Business Times"),
            new NewsItem("Tech Innovations in " + topic, "Tech Weekly")
        );
    }
}