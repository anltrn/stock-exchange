package com.stock.exchange.service.impl;

import com.stock.exchange.dao.StockExchangeRepository;
import com.stock.exchange.dao.StockExchangeStocksRepository;
import com.stock.exchange.dao.StockRepository;
import com.stock.exchange.entity.Stock;
import com.stock.exchange.entity.StockExchange;
import com.stock.exchange.entity.StockExchangeStocks;
import com.stock.exchange.service.StockExchangeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class StockExchangeServiceImpl implements StockExchangeService {
    @Autowired
    private StockExchangeRepository stockExchangeRepository;
    @Autowired
    private StockExchangeStocksRepository stockExchangeStocksRepository;
    @Autowired
    private StockRepository stockRepository;

    @Override
    public void deleteStockFromExchange(String stockExchangeName, String stockName) {
        Stock stock = stockRepository.findByName(stockName)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found with name: " + stockName));

        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName)
                .orElseThrow(() -> new IllegalArgumentException("StockExchange not found with name: " + stockExchangeName));

        StockExchangeStocks association = stockExchangeStocksRepository.findByStockIdAndStockExchangeId(stock.getId(), stockExchange.getId())
                .orElseThrow(() -> new IllegalArgumentException("Association not found between Stock and StockExchange"));
         stockExchangeStocksRepository.delete(association);

        updateLiveInMarketStatus(stock.getId(), stockExchangeName);
    }

    @Override
    public void addStockToExchange(String stockExchangeName, String stockName) {
        Stock stock = stockRepository.findByName(stockName)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found with name: " + stockName));

        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName)
                .orElseThrow(() -> new IllegalArgumentException("StockExchange not found with name: " + stockExchangeName));

        StockExchangeStocks association = new StockExchangeStocks();
        association.setStockId(stock.getId());
        association.setStockExchangeId(stockExchange.getId());

        stockExchangeStocksRepository.save(association);

        updateLiveInMarketStatus(stock.getId(), stockExchangeName);
    }

    private void updateLiveInMarketStatus(Long stockId, String stockExchangeName) {
        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName).get();
        List<StockExchangeStocks> stockExchangeStocks = stockExchangeStocksRepository.findByStockExchangeId(stockExchange.getId()).get();
        boolean liveInMarket = stockExchangeStocks.size() >= 5;
        stockExchange.setLiveInMarket(liveInMarket);
        stockExchangeRepository.save(stockExchange);
    }

    @Override
    public List<Stock> getAllStocksByExchangeName(String stockExchangeName) {
        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName)
                    .orElseThrow(() -> new IllegalArgumentException("StockExchange not found with name: " + stockExchangeName));
        List<Long> stocksIdList = stockExchangeStocksRepository.findStockIdListByStockExchangeId(stockExchange.getId());
        return stockRepository.findAllById(stocksIdList);
    }

    @Override
    public StockExchange createStockExchange(StockExchange stockExchange) {
       return stockExchangeRepository.save(stockExchange);
    }
}
