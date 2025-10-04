package com.example.insightpulse.exception;

import com.example.insightpulse.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

/**
 * Centralized exception handler for the entire application.
 * This class captures specific exceptions and formats them into a consistent
 * {@link ApiResponse} format.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles {@link ApiException} which is thrown when a structured concurrency task fails.
     *
     * @param ex The caught {@link ApiException}.
     * @param exchange The current server exchange.
     * @return A {@link ResponseEntity} containing a formatted {@link ApiResponse}
     *         with a 500 Internal Server Error status.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex, ServerWebExchange exchange) {
        log.error("API exception occurred for request [{}]: {}", exchange.getRequest().getURI(), ex.getMessage(), ex);
        ApiResponse errorResponse = new ApiResponse("One or more data sources failed", ex.getErrorMessages());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}