package com.stock.exchange.service;

import com.stock.exchange.entity.Stock;
import com.stock.exchange.model.StockCreateRequest;

import java.math.BigDecimal;

public interface StockService {
    void deleteStock(Long id);

    void updateStockPrice(Long id, BigDecimal price);

    Stock createStock(StockCreateRequest request);
}
