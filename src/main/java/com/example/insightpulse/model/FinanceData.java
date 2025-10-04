package com.example.insightpulse.model;

/**
 * Represents financial data, such as stock information.
 *
 * @param stockSymbol The ticker symbol for the stock (e.g., "TSLA", "AAPL").
 * @param price The current price of the stock.
 */
public record FinanceData(String stockSymbol, double price) {}