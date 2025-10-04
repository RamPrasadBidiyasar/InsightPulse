package com.example.insightpulse.service;

import com.example.insightpulse.exception.ApiException;
import com.example.insightpulse.model.AggregatedInsight;
import com.example.insightpulse.model.FinanceData;
import com.example.insightpulse.model.NewsItem;
import com.example.insightpulse.model.TrendData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsightServiceTest {

    @Mock
    private NewsService newsService;

    @Mock
    private FinanceService financeService;

    @Mock
    private TrendService trendService;

    @Mock
    private AggregationService aggregationService;

    @InjectMocks
    private InsightService insightService;

    private List<NewsItem> mockNews;
    private FinanceData mockFinanceData;
    private List<TrendData> mockTrendData;

    @BeforeEach
    void setUp() {
        mockNews = List.of(new NewsItem("Test News", "Test Source"));
        mockFinanceData = new FinanceData("TEST", 100.0);
        mockTrendData = List.of(new TrendData("Test Trend", 90));
    }

    @Test
    void getInsights_shouldAggregateDataSuccessfully_givenValidTopic() throws Exception {
        // Arrange
        when(newsService.fetchNews(anyString())).thenReturn(mockNews);
        when(financeService.fetchFinanceData(anyString())).thenReturn(mockFinanceData);
        when(trendService.fetchTrends(anyString())).thenReturn(mockTrendData);
        when(aggregationService.combine(anyString(), any(), any(), any()))
            .thenReturn(new AggregatedInsight("test", mockNews, mockFinanceData, mockTrendData, "Summary"));

        // Act
        AggregatedInsight result = insightService.getInsights("test");

        // Assert
        assertNotNull(result);
        assertEquals("test", result.topic());
        assertEquals(mockNews, result.news());
        assertEquals(mockFinanceData, result.finance());
        assertEquals(mockTrendData, result.trends());
        verify(newsService, times(1)).fetchNews("test");
        verify(financeService, times(1)).fetchFinanceData("test");
        verify(trendService, times(1)).fetchTrends("test");
    }

    @Test
    void getInsights_shouldThrowApiException_whenNewsServiceFails() throws Exception {
        // Arrange
        when(newsService.fetchNews(anyString())).thenThrow(new InterruptedException("News service failed"));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            insightService.getInsights("fail");
        });

        assertTrue(exception.getMessage().contains("Failed to aggregate insights"));
        // Check that the other services were at least started (and then cancelled by the scope)
        verify(financeService, times(1)).fetchFinanceData("fail");
        verify(trendService, times(1)).fetchTrends("fail");
    }
}