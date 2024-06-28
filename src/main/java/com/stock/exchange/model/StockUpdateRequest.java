package com.stock.exchange.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockUpdateRequest {

    @DecimalMin(value = "0", inclusive = false, message = "price should be greater than 0")
    @NotNull(message = "price is required.")
    private BigDecimal price;
    @NotNull(message = "id is required.")
    private Long id;
}
