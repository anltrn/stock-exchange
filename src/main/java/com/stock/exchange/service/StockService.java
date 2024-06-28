package com.stock.exchange.service;

import com.stock.exchange.entity.Stock;
import com.stock.exchange.model.StockCreateRequest;
import com.stock.exchange.model.StockUpdateRequest;

public interface StockService {

    void deleteStock(Long id);

    void updateStockPrice(StockUpdateRequest request);

    Stock createStock(StockCreateRequest request);
}
