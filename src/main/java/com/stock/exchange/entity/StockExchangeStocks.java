package com.stock.exchange.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="STOCK_EXCHANGE_STOCKS",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "stock_id", "stock_exchange_id" }) })
public class StockExchangeStocks {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long stockId;

    @Column
    private Long stockExchangeId;


}