package com.stock.exchange.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockUpdateRequest {

    private BigDecimal price;
    private Long id;
}
