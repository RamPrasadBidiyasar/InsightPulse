package com.example.insightpulse.model;

import java.util.List;

/**
 * A generic API response wrapper for error handling.
 * This is used by the GlobalExceptionHandler to return a consistent error format.
 *
 * @param error A high-level error message.
 * @param details A list of specific error details or reasons.
 */
public record ApiResponse(String error, List<String> details) {}