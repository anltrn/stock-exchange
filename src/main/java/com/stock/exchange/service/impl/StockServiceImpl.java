package com.stock.exchange.service.impl;

import com.stock.exchange.dao.StockExchangeStocksRepository;
import com.stock.exchange.dao.StockRepository;
import com.stock.exchange.entity.Stock;
import com.stock.exchange.entity.StockExchangeStocks;
import com.stock.exchange.model.StockCreateRequest;
import com.stock.exchange.model.StockUpdateRequest;
import com.stock.exchange.service.StockService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockExchangeStocksRepository stockExchangeStocksRepository;
    @Override
    public void deleteStock(Long id) {
        List<StockExchangeStocks> stockExchangeStocks =stockExchangeStocksRepository.findByStockId(id).orElse(null);
        List<Long> targetLongList = stockExchangeStocks.stream()
                .map(stock -> stock.getId())
                .collect(Collectors.toList());
        stockExchangeStocksRepository.deleteAllById(targetLongList);
        stockRepository.deleteById(id);
    }
    @Override
    public void updateStockPrice(StockUpdateRequest request) {
        Stock entity = stockRepository.findById(request.getId()).orElseThrow();
        entity.setCurrentPrice(request.getPrice());
        stockRepository.save(entity);
    }

    @Override
    public Stock createStock(StockCreateRequest request) {
        Stock entity = new Stock();
        entity.setCurrentPrice(request.getCurrentPrice());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return stockRepository.save(entity);
    }
}
