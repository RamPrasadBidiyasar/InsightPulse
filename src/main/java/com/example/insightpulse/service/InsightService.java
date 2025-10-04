package com.example.insightpulse.service;

import com.example.insightpulse.exception.ApiException;
import com.example.insightpulse.model.AggregatedInsight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

/**
 * Service responsible for orchestrating the fetching and aggregation of data
 * from multiple sources using Java 21's Structured Concurrency.
 */
@Service
public class InsightService {

    private static final Logger log = LoggerFactory.getLogger(InsightService.class);

    private final NewsService newsService;
    private final FinanceService financeService;
    private final TrendService trendService;
    private final AggregationService aggregationService;

    public InsightService(
        NewsService newsService,
        FinanceService financeService,
        TrendService trendService,
        AggregationService aggregationService
    ) {
        this.newsService = newsService;
        this.financeService = financeService;
        this.trendService = trendService;
        this.aggregationService = aggregationService;
    }

    /**
     * Fetches and aggregates insights for a given topic by making parallel calls
     * to the news, finance, and trends services.
     *
     * @param topic The keyword or topic to search for.
     * @return An {@link AggregatedInsight} containing the combined data.
     * @throws ApiException if any of the underlying API calls fail.
     */
    public AggregatedInsight getInsights(String topic) {
        log.info("Starting insight aggregation for topic: {}", topic);

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var newsFuture = scope.fork(() -> newsService.fetchNews(topic));
            var financeFuture = scope.fork(() -> financeService.fetchFinanceData(topic));
            var trendsFuture = scope.fork(() -> trendService.fetchTrends(topic));

            // Wait for all subtasks to complete or for the first one to fail.
            scope.join();

            // If any task failed, this will throw the exception that caused the failure.
            // All other running tasks will be cancelled automatically.
            scope.throwIfFailed();

            // All tasks succeeded, so we can now safely get their results.
            log.info("All subtasks completed successfully for topic: {}", topic);
            return aggregationService.combine(
                topic,
                newsFuture.get(),
                financeFuture.get(),
                trendsFuture.get()
            );
        } catch (Exception e) {
            log.error("Error during structured concurrency execution for topic: {}", topic, e);
            // In a real scenario, you might want to collect multiple failures if using ShutdownOnSuccess
            // but with ShutdownOnFailure, we get the first exception.
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            throw new ApiException("Failed to aggregate insights due to one or more service failures.", errors);
        }
    }
}