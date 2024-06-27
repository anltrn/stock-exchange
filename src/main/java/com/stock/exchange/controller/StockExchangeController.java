package com.stock.exchange.controller;

import com.stock.exchange.entity.Stock;
import com.stock.exchange.entity.StockExchange;
import com.stock.exchange.service.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-exchange")
public class StockExchangeController {

    @Autowired
    private StockExchangeService stockExchangeService;
    @DeleteMapping(value="/{name}")
    public ResponseEntity<Void> deleteStockFromExchange(@PathVariable("name") String stockExchangeName,
                                                  @RequestParam("stockName") String stockName) {
        stockExchangeService.deleteStockFromExchange(stockExchangeName, stockName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="/{name}")
    public ResponseEntity<Void> addStockToExchange(@PathVariable("name") String stockExchangeName,
                                                   @RequestParam("stockName") String stockName) {
        stockExchangeService.addStockToExchange(stockExchangeName, stockName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/{name}")
    public ResponseEntity<List<Stock>> stockExchangeStocks(@PathVariable("name") String stockExchangeName){
        List<Stock> stocks = stockExchangeService.getAllStocksByExchangeName(stockExchangeName);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StockExchange> createStockExchange(@RequestBody StockExchange stockExchange) {
        StockExchange createdExchange = stockExchangeService.createStockExchange(stockExchange);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExchange);
    }
}
