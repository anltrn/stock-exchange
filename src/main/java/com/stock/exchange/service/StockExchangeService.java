package com.stock.exchange.service;

import com.stock.exchange.entity.Stock;
import com.stock.exchange.entity.StockExchange;

import java.util.List;

public interface StockExchangeService {

    void deleteStockFromExchange(String stockExchangeName, String stockName);
    void addStockToExchange(String stockExchangeName, String stockName);
    List<Stock> getAllStocksByExchangeName(String stockExchangeName);
    StockExchange createStockExchange(StockExchange stockExchange);
}
