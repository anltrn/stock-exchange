package com.stock.exchange.service.impl;

import com.stock.exchange.dao.StockExchangeRepository;
import com.stock.exchange.dao.StockRepository;
import com.stock.exchange.entity.Stock;
import com.stock.exchange.entity.StockExchange;
import com.stock.exchange.service.StockExchangeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class StockExchangeServiceImpl implements StockExchangeService {
    @Autowired
    private StockExchangeRepository stockExchangeRepository;
    @Autowired
    private StockRepository stockRepository;

    @Override
    public void deleteStockFromExchange(String stockExchangeName, String stockName) {
        Stock stock = stockRepository.findByName(stockName)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found with name: " + stockName));

        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName)
                .orElseThrow(() -> new EntityNotFoundException("StockExchange not found with name: " + stockExchangeName));

        stock.removeFromStockExchange(stockExchange);
        boolean liveInMarket = stockExchange.getStocks().size() >= 5;
        stockExchange.setLiveInMarket(liveInMarket);
        stockExchangeRepository.save(stockExchange);
    }

    @Override
    public void addStockToExchange(String stockExchangeName, String stockName) {
        Stock stock = stockRepository.findByName(stockName)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found with name: " + stockName));

        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName)
                .orElseThrow(() -> new EntityNotFoundException("StockExchange not found with name: " + stockExchangeName));

        stock.addToStockExchange(stockExchange);
        boolean liveInMarket = stockExchange.getStocks().size() >= 5;
        stockExchange.setLiveInMarket(liveInMarket);
        stockExchangeRepository.save(stockExchange);
    }


    @Override
    public StockExchange getAllStocksByExchangeName(String stockExchangeName) {
        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName)
                .orElseThrow(() -> new EntityNotFoundException("StockExchange not found with name: " + stockExchangeName));
        return stockExchange;
    }
}
