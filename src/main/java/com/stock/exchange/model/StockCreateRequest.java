package com.stock.exchange.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockCreateRequest {

    private String name;
    private String description;
    private BigDecimal currentPrice;
}
