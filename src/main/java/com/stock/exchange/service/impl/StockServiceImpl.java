package com.stock.exchange.service.impl;

import com.stock.exchange.dao.StockExchangeRepository;
import com.stock.exchange.dao.StockRepository;
import com.stock.exchange.entity.Stock;
import com.stock.exchange.entity.StockExchange;
import com.stock.exchange.model.StockCreateRequest;
import com.stock.exchange.service.StockService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Transactional
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Override
    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Stock not found with id: " + id));
        for(StockExchange stockExchange : stock.getStockExchanges()){
            boolean liveInMarket = stockExchange.getStocks().size() > 5;
            stockExchange.setLiveInMarket(liveInMarket);
        }
        stockExchangeRepository.saveAll(stock.getStockExchanges());
        stockRepository.deleteById(id);
    }

    @Override
    public void updateStockPrice(Long id, BigDecimal price) {
        Stock entity = stockRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Stock not found with id: " + id));
        entity.setCurrentPrice(price);
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
