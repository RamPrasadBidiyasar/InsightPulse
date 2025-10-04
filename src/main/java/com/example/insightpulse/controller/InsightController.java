package com.example.insightpulse.controller;

import com.example.insightpulse.model.AggregatedInsight;
import com.example.insightpulse.service.InsightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for aggregating and serving insights.
 * Provides an endpoint to fetch combined data from multiple sources for a given topic.
 */
@RestController
@RequestMapping("/api/v1/insights")
public class InsightController {

    private final InsightService insightService;

    public InsightController(InsightService insightService) {
        this.insightService = insightService;
    }

    /**
     * Aggregates insights for the given topic from multiple data sources concurrently.
     *
     * @param topic The topic to fetch insights for.
     * @return A {@link ResponseEntity} containing the {@link AggregatedInsight} on success,
     *         or an error response if any data source fails.
     */
    @GetMapping("/{topic}")
    public ResponseEntity<AggregatedInsight> getInsights(@PathVariable String topic) {
        AggregatedInsight aggregatedInsight = insightService.getInsights(topic);
        return ResponseEntity.ok(aggregatedInsight);
    }
}