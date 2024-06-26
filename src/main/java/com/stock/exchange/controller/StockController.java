package com.stock.exchange.controller;

import com.stock.exchange.entity.Stock;
import com.stock.exchange.model.StockCreateRequest;
import com.stock.exchange.model.StockUpdateRequest;
import com.stock.exchange.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    @Autowired
    private StockService stockService;
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteStockById(@PathVariable("id") Long id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<Void> updateStockPrice(@RequestBody StockUpdateRequest request) {
        stockService.updateStockPrice(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Stock> createStock(@RequestBody StockCreateRequest request) {
        return new ResponseEntity<>(stockService.createStock(request), HttpStatus.OK);
    }

}
