package com.example.insightpulse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

/**
 * Maps external configuration from {@code application.yml} to a typed Java object.
 * This class holds settings for external API clients, such as URLs and timeouts.
 *
 * @param clients A map where each key is a client name (e.g., "news", "finance")
 *                and the value is a {@link ClientProperties} object containing
 *                the base URL and timeout for that client.
 */
@ConfigurationProperties(prefix = "app")
public record AppProperties(Map<String, ClientProperties> clients) {

    /**
     * Configuration properties for a single external API client.
     *
     * @param baseUrl The base URL of the external API.
     * @param timeout The connection and response timeout duration.
     */
    public record ClientProperties(String baseUrl, Duration timeout) {}
}