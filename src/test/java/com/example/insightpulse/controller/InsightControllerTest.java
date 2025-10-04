package com.example.insightpulse.controller;

import com.example.insightpulse.model.AggregatedInsight;
import com.example.insightpulse.model.FinanceData;
import com.example.insightpulse.model.NewsItem;
import com.example.insightpulse.model.TrendData;
import com.example.insightpulse.service.InsightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.List;
import static org.mockito.BDDMockito.given;

@WebFluxTest(InsightController.class)
class InsightControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private InsightService insightService;

    @Test
    void getInsights_shouldReturnAggregatedData_whenServiceSucceeds() {
        // Arrange
        String topic = "tech";
        AggregatedInsight mockInsight = new AggregatedInsight(
            topic,
            List.of(new NewsItem("New CPU Released", "TechCrunch")),
            new FinanceData("INTC", 55.0),
            List.of(new TrendData("AI Chips", 95)),
            "Summary for tech"
        );
        given(insightService.getInsights(topic)).willReturn(mockInsight);

        // Act & Assert
        webTestClient.get().uri("/api/v1/insights/{topic}", topic)
            .exchange()
            .expectStatus().isOk()
            .expectBody(AggregatedInsight.class)
            .isEqualTo(mockInsight);
    }

    @Test
    void getInsights_shouldReturnError_whenServiceFails() {
        // Arrange
        String topic = "fail";
        given(insightService.getInsights(topic)).willThrow(new RuntimeException("Service failure"));

        // Act & Assert
        webTestClient.get().uri("/api/v1/insights/{topic}", topic)
            .exchange()
            .expectStatus().is5xxServerError();
    }
}