package com.example.insightpulse.exception;

import java.util.List;

/**
 * Custom exception thrown when one or more external API calls fail.
 * It holds a list of error messages from the failed subtasks.
 */
public class ApiException extends RuntimeException {

    private final List<String> errorMessages;

    public ApiException(String message, List<String> errorMessages) {
        super(message);
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}