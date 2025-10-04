package com.example.insightpulse.service;

import com.example.insightpulse.model.TrendData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service responsible for fetching trends data from a simulated external trends API.
 */
@Service
public class TrendService {

    private static final Logger log = LoggerFactory.getLogger(TrendService.class);

    /**
     * Fetches trends for a given topic.
     * This is a mock implementation that returns a dummy list of trends.
     *
     * @param topic The topic to fetch trends for.
     * @return A list of {@link TrendData} objects.
     * @throws InterruptedException if the thread is interrupted while sleeping.
     */
    public List<TrendData> fetchTrends(String topic) throws InterruptedException {
        log.info("Fetching trends for topic: {}", topic);
        // Simulate network latency
        Thread.sleep(800);
        if ("error".equalsIgnoreCase(topic)) {
            throw new RuntimeException("Trends API is down");
        }
        // Mock data
        return List.of(
            new TrendData(topic + " popularity", 88),
            new TrendData("Related " + topic + " news", 75)
        );
    }
}