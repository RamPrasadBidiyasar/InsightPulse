package com.example.insightpulse.service;

import com.example.insightpulse.model.FinanceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service responsible for fetching financial data from a simulated external finance API.
 */
@Service
public class FinanceService {

    private static final Logger log = LoggerFactory.getLogger(FinanceService.class);

    /**
     * Fetches financial data for a given topic.
     * This is a mock implementation that returns dummy finance data.
     *
     * @param topic The topic to fetch finance data for.
     * @return A {@link FinanceData} object.
     * @throws InterruptedException if the thread is interrupted while sleeping.
     */
    public FinanceData fetchFinanceData(String topic) throws InterruptedException {
        log.info("Fetching finance data for topic: {}", topic);
        // Simulate network latency
        Thread.sleep(1200);
        if ("error".equalsIgnoreCase(topic)) {
            throw new RuntimeException("Finance API unavailable");
        }
        // Mock data
        return new FinanceData(topic.toUpperCase() + "SYM", 150.75);
    }
}