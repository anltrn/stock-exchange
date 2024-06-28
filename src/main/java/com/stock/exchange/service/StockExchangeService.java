package com.stock.exchange.service;

import com.stock.exchange.entity.StockExchange;

public interface StockExchangeService {

    void deleteStockFromExchange(String stockExchangeName, String stockName);

    void addStockToExchange(String stockExchangeName, String stockName);

    StockExchange getAllStocksByExchangeName(String stockExchangeName);

    StockExchange createStockExchange(StockExchange stockExchange);
}
