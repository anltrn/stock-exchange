package com.stock.exchange.controller;

import com.stock.exchange.entity.StockExchange;
import com.stock.exchange.service.StockExchangeService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock-exchange")
@Validated
public class StockExchangeController {

    @Autowired
    private StockExchangeService stockExchangeService;

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<Void> deleteStockFromExchange(@PathVariable("name") @NotBlank(message = "stock exchange can not be blank") String stockExchangeName,
                                                        @RequestParam("stockName") @NotBlank(message = "stock name can not be blank") String stockName) {
        stockExchangeService.deleteStockFromExchange(stockExchangeName, stockName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{name}")
    public ResponseEntity<Void> addStockToExchange(@PathVariable("name") @NotBlank(message = "stock exchange can not be blank") String stockExchangeName,
                                                   @RequestParam("stockName") @NotBlank(message = "stock name can not be blank") String stockName) {
        stockExchangeService.addStockToExchange(stockExchangeName, stockName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<StockExchange> stockExchangeStocks(@PathVariable("name")
                                                             @NotBlank(message = "stock exchange can not be blank") String stockExchangeName) {
        StockExchange stockExchange = stockExchangeService.getAllStocksByExchangeName(stockExchangeName);
        return new ResponseEntity<>(stockExchange, HttpStatus.OK);
    }
}
