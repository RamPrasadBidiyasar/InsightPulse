package com.example.insightpulse.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Configures {@link WebClient} beans for making HTTP requests to external APIs.
 * This class reads configuration from {@link AppProperties} to set up base URLs and timeouts
 * for each external service client.
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class WebClientConfig {

    /**
     * Creates a {@link WebClient} bean specifically for the News API.
     *
     * @param appProperties The application properties containing the client configuration.
     * @return A configured {@link WebClient} instance for the News API.
     */
    @Bean
    public WebClient newsWebClient(AppProperties appProperties) {
        return createWebClientForClient("news", appProperties);
    }

    /**
     * Creates a {@link WebClient} bean specifically for the Finance API.
     *
     * @param appProperties The application properties containing the client configuration.
     * @return A configured {@link WebClient} instance for the Finance API.
     */
    @Bean
    public WebClient financeWebClient(AppProperties appProperties) {
        return createWebClientForClient("finance", appProperties);
    }

    /**
     * Creates a {@link WebClient} bean specifically for the Trends API.
     *
     * @param appProperties The application properties containing the client configuration.
     * @return A configured {@link WebClient} instance for the Trends API.
     */
    @Bean
    public WebClient trendsWebClient(AppProperties appProperties) {
        return createWebClientForClient("trends", appProperties);
    }

    /**
     * Helper method to construct a {@link WebClient} with a specific base URL and timeout settings.
     *
     * @param clientName The name of the client (e.g., "news", "finance") to look up in properties.
     * @param properties The application properties.
     * @return A fully configured {@link WebClient}.
     * @throws IllegalArgumentException if no configuration is found for the given client name.
     */
    private WebClient createWebClientForClient(String clientName, AppProperties properties) {
        AppProperties.ClientProperties clientProperties = properties.clients().get(clientName);
        if (clientProperties == null) {
            throw new IllegalArgumentException("Configuration for client '" + clientName + "' not found in application properties.");
        }

        HttpClient httpClient = HttpClient.create()
            .doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(clientProperties.timeout().toMillis(), TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(clientProperties.timeout().toMillis(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
            .baseUrl(clientProperties.baseUrl())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }
}