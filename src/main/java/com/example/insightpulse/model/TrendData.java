package com.example.insightpulse.model;

/**
 * Represents trending topics or keywords.
 *
 * @param keyword The trending keyword.
 * @param popularity A score representing the trend's popularity (e.g., 0-100).
 */
public record TrendData(String keyword, int popularity) {}