package com.stock.exchange.controller;

import com.stock.exchange.entity.Stock;
import com.stock.exchange.model.StockCreateRequest;
import com.stock.exchange.service.StockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/stock")
@Validated
public class StockController {

    @Autowired
    private StockService stockService;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStockById(@PathVariable("id") @Positive Long id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> updateStockPrice(@PathVariable("id") @Positive Long id,
                                                 @RequestParam("price")
                                                 @Positive(message = "Price should be positive") BigDecimal price) {
        stockService.updateStockPrice(id, price);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@Valid @RequestBody StockCreateRequest request) {
        return new ResponseEntity<>(stockService.createStock(request), HttpStatus.CREATED);
    }

}
